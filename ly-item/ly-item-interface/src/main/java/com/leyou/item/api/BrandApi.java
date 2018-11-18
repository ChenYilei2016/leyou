package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/12-15:44
 * hello everyone
 */

@RequestMapping("/brand")
public interface BrandApi {
    //根据id查
    @GetMapping("/{id}")
    public Brand queryById(@PathVariable("id")Long id);

    @GetMapping("/list")
    public List<Brand> queryBrandsByIds(@RequestParam("ids")List<Long> ids );
}
