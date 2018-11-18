package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/11-19:57
 * hello everyone
 */

@RequestMapping("/category")
public interface CategoryApi {

    @GetMapping("/list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
