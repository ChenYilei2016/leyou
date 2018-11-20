package com.leyou.sms.listener;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
        map.put("code","666666");
        rabbitTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",map);
//        redisTemplate.opsForValue().set("23","13",10000,TimeUnit.MILLISECONDS);
    }

    @Test
    public void send2(){
        Map<String,String> map = new HashMap<>();
        map.put("phone","17758163513");
        map.put("code","535435");
//        rabbitTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",map);
//        redisTemplate.opsForValue().set("23","13",10000,TimeUnit.MILLISECONDS);
        String json = JSONObject.toJSONString(map);
        Map<String,String> map2 = (Map)JSONObject.parseObject(json);
        System.out.println(  map2.get("phone") );

        System.out.println( getGenerateCode(6));
    }

    public String getGenerateCode(int count){
        StringBuilder sb = new StringBuilder("");
        Random random =new Random();
        while (count-- >0){
            sb.append( random.nextInt(10) ); // 0 - 9
        }
        return sb.toString();
    }

}
