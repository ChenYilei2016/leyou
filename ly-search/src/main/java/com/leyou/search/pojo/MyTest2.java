package com.leyou.search.pojo;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.controller.SearchController;
import com.leyou.search.repository.GoodsRepositroy;
import com.leyou.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenyilei
 * @date 2018/11/12-15:52
 * hello everyone
 */


@Component
@Order(1)
public class MyTest2 {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;


    @Autowired
    GoodsClient goodsClient;

    @Autowired
    SearchService searchService;

    @Autowired
    GoodsRepositroy goodsRepositroy;

    @Test
    public void create(){
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData(){
        int page = 1;
        int rows = 100;
        //查所有 spu -> goods
        PageResult<Spu> spuPageResult = goodsClient.querySpuByPage(page, rows, null, true);
        List<Spu> items = spuPageResult.getItems();
        List<Goods> goods = items.stream()
                .map(x -> searchService.buildGoods(x)).collect(Collectors.toList());

        //导入 el库
        goodsRepositroy.saveAll(goods);
    }


}
