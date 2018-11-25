package com.leyou.order.controller;

import com.leyou.common.utils.IdWorker;
import com.leyou.item.pojo.OrderDto;
import com.leyou.order.config.IdWorkerProperties;
import com.leyou.order.pojo.Order;
import com.leyou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author chenyilei
 * @date 2018/11/24-10:38
 * hello everyone
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    //创建订单
    @PostMapping
    public ResponseEntity<Long> createOrder(
            @RequestBody @Valid OrderDto orderDto
            ){
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    //查询订单
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> queryOrderById(@PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(orderService.queryOrderById(orderId) );
    }

    // 统一下单 生成二维码的 订单/连接
    @GetMapping("/url/{id}")
    public ResponseEntity<String> createPayUrl(@PathVariable("id") Long orderId){
        log.warn("进入二维码生成");
        return ResponseEntity.ok(orderService.createPayUrl(orderId));
    }

    /**
     * 0 未支付
     * 1 已支付
     * 2 支付失败
     * @param id
     * @return
     */
    @GetMapping("/state/{id}")
    public ResponseEntity<Integer> queryOrderStatu(@PathVariable("id")Long id){
        return ResponseEntity.ok(orderService.queryStatu(id));
    }

}
