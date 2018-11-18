package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.BrandService;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/04-9:48
 * hello everyone
 */
@Controller
@ResponseBody
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResult<Brand> result = this.brandService.queryBrandByPageAndSort(page,rows,sortBy,desc, key);
        if (result == null || result.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,
                                          @RequestParam("categories") List<Long> ids){

        brandService.save(brand,ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //根据 cid 获得有的 品牌 列表
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCategoryId(@PathVariable("cid") Long cid){
        return ResponseEntity.ok( brandService.queryBrandByCategoryId(cid) ) ;

    }

    //根据id查
    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryById(@PathVariable("id")Long id){
        return ResponseEntity.ok(brandService.queryByBid(id));
    }


    //多个id的 查询多个brand @RequestMapping("/brand")
    @GetMapping("/list")
    public ResponseEntity<List<Brand>> queryBrandsByIds(@RequestParam("ids")List<Long> ids ){
        return ResponseEntity.ok(brandService.queryBrandsByIds(ids));
    }
}
