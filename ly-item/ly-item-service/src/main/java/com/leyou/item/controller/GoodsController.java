package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.pojo.*;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/09-16:03
 * hello everyone
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    //查询 spu列表
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable",defaultValue = "")Boolean saleable ) {
        // 分页查询spu信息
        PageResult<Spu> result = this.goodsService.querySpuByPageAndSort(page, rows,saleable ,key);
        if (result == null || result.getItems().size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 新增商品
     * @param spu
     * @return
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu) {
        try {
            this.goodsService.save(spu);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //根据spuid 查 spuDetail
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("id") Long id) {
        SpuDetail detail = this.goodsService.querySpuDetailBySpuId(id);
        if (detail == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(detail);
    }

    //根据spuid 查询所有的 sku对象 并且附上 stock库存量
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id) {
        List<Sku> skus = this.goodsService.querySkuBySpuId(id);
        if (skus == null || skus.size() == 0) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(skus);
    }
    //TODO TODO: 商品的删除、上下架大家自行实现。



    @GetMapping("spu/{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        //查spu
        Spu spu = this.goodsService.querySpuById(id);

        return ResponseEntity.ok(spu);
    }

    @GetMapping("/sku/list/ids")
    public ResponseEntity<List<Sku>> querySkusByIds(@RequestParam("ids")List<Long> ids){
        return ResponseEntity.ok( goodsService.querySkusByIds(ids) );
    }

    @PostMapping("/stock/decrease")
    public ResponseEntity<Void> stockDecrease(
            @RequestBody List<CartDto> carts){
        goodsService.decreaseStock(carts);
        return ResponseEntity.ok(null);
    }

}

