package com.leyou.search.service;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.common.pojo.PageResult;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.*;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepositroy;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenyilei
 * @date 2018/11/13-13:42
 * hello everyone
 */

@Service
@Slf4j
public class SearchService {
    @Autowired
    BrandClient brandClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    GoodsClient goodsClient;
    @Autowired
    SpecificationClient specificationClient;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    GoodsRepositroy goodsRepositroy;

    public Goods buildGoods(Spu spu){
        Goods goods = new Goods();
        //已有字段
        goods.setId(spu.getId());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());

        // all 搜索字段查询
        String all = spu.getSubTitle();
        List<Category> categories = categoryClient.queryCategoryByIds
                (Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<String> names = categories.stream().map(x -> x.getName()).collect(Collectors.toList());
        all += StringUtils.join(names," ");

        Brand brand = brandClient.queryById(spu.getId());
        if(brand == null)
            brand = new Brand();
        all+= brand.getName();
        goods.setAll(all);

        // 价格集合
        List<Sku> skus = goodsClient.querySkuBySpuId(spu.getId());
        Set<Long> priceSet = skus.stream().map(x -> x.getPrice()).collect(Collectors.toSet());
        goods.setPrice(priceSet);

        //sku集合
        List<Map<String,Object>> skuList = new ArrayList<>();
        for(Sku temp : skus){
            Map<String,Object> map = new HashMap<>();
            map.put("id",temp.getId());
            map.put("price",temp.getPrice());
            map.put("title",temp.getTitle());
            map.put("image",StringUtils.substringBefore(temp.getImages(),","));
            skuList.add(map);
        }
        goods.setSkus(JSONArray.toJSONString(skuList) );

        //key 在 param规格参数表  , value 在商品详情表中 spudetail的属性
        Map<String,Object> specs = new HashMap<>();

        List<SpecParam> specParams = specificationClient.queryParams(null, spu.getCid3(), true, false);
        List<SpecParam> specParams2 = specificationClient.queryParams(null, spu.getCid3(), false, true);
        specParams.addAll(specParams2);
        SpuDetail spuDetail = goodsClient.querySpuDetailBySpuId(spu.getId());
        //共有属性 | "1" : "公共属性"
        Map<Long, String> genericSpec = JsonUtils.parseMap(spuDetail.getGenericSpec(), Long.class, String.class);
        //私有属性 |  "2" : "[红,绿,蓝]" -->  "颜色" :"[红,绿,蓝]"
        Map<Long, List<String>> specialSpec = JsonUtils.
                nativeRead(spuDetail.getSpecialSpec(), new TypeReference<Map<Long, List<String>>>() {
        });
        for(SpecParam param: specParams){
            String key = param.getName();
            Object value = "";
            if( param.getGeneric() ){
                value =genericSpec.get( param.getId() );

                //公共属性 直接弄成段! 查询方便
                if( param.getNumeric() ){
                    value = chooseSegment(value.toString(),param);
                }

            }else{
                value =specialSpec.get(param.getId());
            }
            specs.put(key,value);
        }
        goods.setSpecs(specs);//TODO:可搜索的规格
        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public PageResult<Goods> search(SearchRequest searchRequest) {
        int page= searchRequest.getPage() - 1;
        int size= searchRequest.getSize();
        String key = searchRequest.getKey();

        NativeSearchQueryBuilder nativeSearchQueryBuilder= new NativeSearchQueryBuilder();
        //es 选取有用字段
        nativeSearchQueryBuilder
                .withSourceFilter(new FetchSourceFilter(new String[]{"id","subTitle","skus"},null));

        //es 关键字过滤 包括一些规格过滤
        QueryBuilder basicQuery = buildQueryFilter(searchRequest);
        nativeSearchQueryBuilder.withQuery(basicQuery);

        //es 分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page,size));

        //聚合
        String categoryAggName = "category_agg";
        String brandAggName="brand_agg";
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));


        AggregatedPage<Goods> result = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), Goods.class);

        long total = result.getTotalElements();
        int totalpage = result.getTotalPages();
        List<Goods> goods = result.getContent();
        //聚合的属性
        List<Category> categories = getCategoryAggResult( result.getAggregation(categoryAggName) );
        List<Brand> brands = getBrandAggResult(result.getAggregation(brandAggName) );

        List<Map<String,Object>> specs = new ArrayList<>();
        if( categories != null &&categories.size() == 1 ){
            specs = getSpec(categories.get(0).getId(),basicQuery);
        }

        SearchResult sr = new SearchResult(total,(long)totalpage,goods,categories,brands,specs);
        return sr;
    }

    private QueryBuilder buildQueryFilter(SearchRequest searchRequest) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must( QueryBuilders.matchQuery("all",searchRequest.getKey()) );

        Map<String, String> map = searchRequest.getFilter();

        for(Map.Entry<String,String> entry: map.entrySet() ){
            String key = entry.getKey();
            if( !"cid3".equals(key) && !"brandId".equals(key) ){
                key = "specs."+key+".keyword";
            }
            boolQueryBuilder.filter(QueryBuilders.termQuery(key,entry.getValue()));
        }
        return boolQueryBuilder;
    }

    //获得 属性集:  一个商品分类的 :[ 颜色:[红,绿] ,颜色:[红,绿] ]
    private List<Map<String,Object>> getSpec(Long cid, QueryBuilder basicQuery) {
        List<Map<String,Object>> specs = new ArrayList<>();
        List<SpecParam> specParams = specificationClient.queryParams(null, cid, true, false);
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(basicQuery);

        for (SpecParam specParam : specParams) {
            String name = specParam.getName();
            nativeSearchQueryBuilder.
                    addAggregation(AggregationBuilders.terms(name).field("specs."+name+".keyword"));
        }

        AggregatedPage<Goods> goods =
                elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.build(), Goods.class);

        for (SpecParam specParam : specParams) {
            String name = specParam.getName();

            StringTerms aggregation = (StringTerms)goods.getAggregation(name);
            List<String> options = aggregation.getBuckets().stream()
                    .map(b -> b.getKeyAsString()).collect(Collectors.toList());

            Map<String ,Object> map = new HashMap<>();
            map.put("k",name);
            map.put("options",options);
            specs.add(map);
        }

        return specs;
    }

    // 解析品牌聚合结果
    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        try {
            LongTerms brandAgg = (LongTerms) aggregation;
            List<Long> bids = new ArrayList<>();
            for (LongTerms.Bucket bucket : brandAgg.getBuckets()) {
                bids.add(bucket.getKeyAsNumber().longValue());
            }
            // 根据id查询品牌
            return this.brandClient.queryBrandsByIds(bids);
        } catch (Exception e){
            log.error("品牌聚合出现异常：", e);
            return null;
        }
    }

    // 解析商品分类聚合结果
    private List<Category> getCategoryAggResult(Aggregation aggregation) {
        try{
            List<Category> categories = new ArrayList<>();
            LongTerms categoryAgg = (LongTerms) aggregation;
            List<Long> cids = new ArrayList<>();
            for (LongTerms.Bucket bucket : categoryAgg.getBuckets()) {
                cids.add(bucket.getKeyAsNumber().longValue());
            }
            // 根据id查询分类名称
            List<String> names = this.categoryClient.queryCategoryByIds(cids).stream().map(x->x.getName()).collect(Collectors.toList());

            for (int i = 0; i < names.size(); i++) {
                Category c = new Category();
                c.setId(cids.get(i));
                c.setName(names.get(i));
                categories.add(c);
            }
            return categories;
        } catch (Exception e){
            log.error("分类聚合出现异常：", e);
            return null;
        }
    }
}
