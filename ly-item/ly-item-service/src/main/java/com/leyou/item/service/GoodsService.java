package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.exception.LyException;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenyilei
 * @date 2018/11/09-15:46
 * hello everyone
 */

@Service
@Slf4j
public class GoodsService {

    @Autowired
    SpuMapper spuMapper;

    @Autowired
    SkuMapper skuMapper;

    @Autowired
    StockMapper stockMapper;

    @Autowired
    SpuDetailMapper spuDetailMapper;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Transactional
    public PageResult<Spu> querySpuByPageAndSort(Integer page, Integer rows, Boolean saleable, String key) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Spu.class);

        example.setOrderByClause("last_update_time desc");

        if(!StringUtils.isEmpty(key)){
            example.createCriteria().andLike("title","%"+key+"%");
        }
        if(saleable != null){
            example.createCriteria().andEqualTo("saleable",saleable);
        }
        List<Spu> spus = spuMapper.selectByExample(example);

        //装载 额外参数
        loadCategoryNameAndBrandName(spus);

        PageInfo pageInfo = new PageInfo(spus);

        return new PageResult<>(pageInfo.getTotal(),spus);
    }

    private void loadCategoryNameAndBrandName(List<Spu> spus) {

        spus.stream().forEach( (spu)->{
            // 得到3个 category 组合名字
            List<String> nameList = categoryService.queryByIdList(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())).stream()
                    .map((c) -> c.getName()).collect(Collectors.toList());
            spu.setCname( StringUtils.join(nameList,"/") );

            // 得到品牌名字
            Brand brand = brandService.queryByBid(spu.getBrandId());
            spu.setBname(brand.getName());
        } );
    }

    @Transactional
    public void save(Spu spu) {
        // spu
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(true);
        int count = spuMapper.insert(spu);

        if(count != 1){
            throw new LyException("spu save error");
        }
        //spudetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());

        List<Stock> stocks = new ArrayList<>();

        //sku
        spu.getSkus().stream().forEach(sku->{
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setEnable(true);
            sku.setLastUpdateTime(sku.getCreateTime());
            skuMapper.insert(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        });
        //stock
        int i = stockMapper.insertList(stocks);

        if(i != stocks.size()){
            throw new LyException("insert stocks list == 0");
        }

        try {
            sendMessage(spu.getId(),"save");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public SpuDetail querySpuDetailBySpuId(Long id) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
        return spuDetail;
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        //查询得出原始 sku的列表
        List<Sku> skus = skuMapper.select(sku);

        // 填充sku列表中 stock的值
        List<Long> skuIdList = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stocks = stockMapper.selectByIdList(skuIdList);
        Map<Long, Integer> skuId_StockValue = stocks.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skus.forEach( x ->{
            x.setStock( skuId_StockValue.get(x.getId()) );
        });
        return skus;
    }

    public Spu querySpuById(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);

        //sku
        spu.setSkus(querySkuBySpuId(id));

        //detail
        spu.setSpuDetail(querySpuDetailBySpuId(id) );

        return spu;
    }

    private void sendMessage(Long id, String type){
        // 发送消息
        try {
            this.amqpTemplate.convertAndSend("item." + type, id);
        } catch (Exception e) {
            log.error("{}商品消息发送异常，商品id：{}", type, id, e);
        }
    }

    public List<Sku> querySkusByIds(List<Long> ids) {
        return skuMapper.selectByIdList(ids);
    }

    @Transactional
    public void decreaseStock(List<CartDto> carts) {
        for (CartDto cart : carts) {
            // update  stock= stock -1 and stock >=1
            int count = stockMapper.decreaseStock(cart.getSkuId(), cart.getNum());
            if(1!= count){
                throw new RuntimeException("库存不足--防止超卖");
            }
        }
    }
}
