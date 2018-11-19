package com.leyou.sms.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chenyilei
 * @date 2018/11/19-19:39
 * hello everyone
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TESTM {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void send(){
        Map<String,String> map = new HashMap<>();
        map.put("phone","17758163513");
        map.put("code","543543");
        rabbitTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",map);
//        redisTemplate.opsForValue().set("23","13",10000,TimeUnit.MILLISECONDS);
    }

}
