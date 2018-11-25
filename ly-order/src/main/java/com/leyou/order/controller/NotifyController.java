package com.leyou.order.controller;

import com.leyou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/11/25-9:35
 * hello everyone
 */
@RestController
@RequestMapping("/notify")
@Slf4j
public class NotifyController {
    @Autowired
    OrderService orderService;

    @PostMapping(produces = "application/xml")
    //要弄一个 xml格式的消息转换器
    public Map<String,String> WXNotify(@RequestBody Map<String,String> result){
        log.info("微信回调了:{}",result);
        orderService.handleNotify(result);
        log.info("订单回调成功");

        //应答 return_code return_msg
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("return_code","SUCCESS");
        returnMap.put("return_msg","OK");
        return returnMap;
    }

}
