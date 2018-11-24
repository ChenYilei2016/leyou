package com.leyou.controller;

import com.leyou.bean.Cart;
import com.leyou.bean.Spu;
import com.leyou.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/23-16:14
 * hello everyone
 */

//- 第一层Map，Key是用户id
//- 第二层Map，Key是购物车中商品id，值是购物车数据
//   Map<String,Map<String,String>>

@Slf4j
@RestController
public class CartController {

    @Autowired
    CartService cartService;

    //查询商品列表
    @PostMapping("/list")
    public ResponseEntity<List<Cart>> queryCartList(){
        return ResponseEntity.ok(cartService.queryCartList());
    }

    //新增商品
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        this.cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateCartNum(@RequestParam("id") Long skuId,@RequestParam("num") Integer num){
        cartService.updateNum(skuId,num);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{skuId}")
    public ResponseEntity<Void> updateCartNum(@PathVariable("skuId") Long skuId ){
        cartService.deleteCart(skuId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping("/test")
    public void testt(@RequestBody Spu spu){
        log.info("spu{}",spu);
    }
}
