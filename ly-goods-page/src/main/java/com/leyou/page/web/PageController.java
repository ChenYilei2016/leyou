package com.leyou.page.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/11/17-10:32
 * hello everyone
 */

@Controller
public class PageController {

    @GetMapping("/item/{id}.html")
    public ModelAndView toItemPageDetail(@PathVariable("id")Long spuId,
                                         Map map){



        return new ModelAndView("item",map);
    }
}
