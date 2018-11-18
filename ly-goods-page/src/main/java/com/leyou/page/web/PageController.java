package com.leyou.page.web;

import com.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.Id;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/11/17-10:32
 * hello everyone
 */

@Controller
public class PageController {
    @Autowired
    PageService pageService;
//    categories
//    brand
//    spu
//    detail 详情
//    skus
//    specs
    @GetMapping("/item/{id}.html")
    public ModelAndView toItemPageDetail(@PathVariable("id")Long spuId,
                                         Map map){
        //寻找静态页

        //没有页面的时候再重新查找数据
        pageService.loadModel(map,spuId);

        return new ModelAndView("item",map);
    }
}
