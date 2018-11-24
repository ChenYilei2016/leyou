package com.leyou.order.controller;

import com.leyou.common.utils.IdWorker;
import com.leyou.item.pojo.OrderDto;
import com.leyou.order.config.IdWorkerProperties;
import com.leyou.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author chenyilei
 * @date 2018/11/24-10:38
 * hello everyone
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(
            @RequestBody @Valid OrderDto orderDto
            ){
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }
}
