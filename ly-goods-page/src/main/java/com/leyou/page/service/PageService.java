package com.leyou.page.service;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/11/17-15:35
 * hello everyone
 */
@Service
@Slf4j
public class PageService {
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecificationClient specificationClient;

    //模版引擎
    @Autowired
    TemplateEngine templateEngine;

    public static final String SAVE_PATH="C:/Users/chenyilei/Desktop/日语/TEST";

    //    categories
//    brand
//    spu
//    detail 详情
//    skus
//    specs

    public void loadModel(Map map, Long spuId) {
        Spu spu = goodsClient.querySpuById(spuId);
        map.put("spu",spu);
        map.put("detail",spu.getSpuDetail());
        map.put("skus",spu.getSkus());

        Brand brand = brandClient.queryById(spu.getBrandId());
        map.put("brand",brand);

        List<SpecGroup> specGroups = specificationClient.querySpecsGroupsByCid(spu.getCid3());
        map.put("specs",specGroups);

        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        map.put("categories",categories);

        //log.error("specs:"+specGroups);;
        //log.error("skus:"+spu.getSkus());
    }

    public void createHtml(Map map){
        Context context = new Context();
        context.setVariables(map);

        String name = ((Spu)map.get("spu")).getId()+".html";
        File file = new File(SAVE_PATH,name);

        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            templateEngine.process("item",context,out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

