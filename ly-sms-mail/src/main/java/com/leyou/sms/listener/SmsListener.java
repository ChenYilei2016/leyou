package com.leyou.sms.listener;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.leyou.sms.utils.SmsUtils;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/11/19-17:51
 * hello everyone
 */

@Component
@Slf4j
public class SmsListener {

    @Autowired
    SmsUtils smsUtils;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @RabbitListener(bindings =
        @QueueBinding(
            value = @Queue(value = "sms.verify.code.queue",durable ="true")
           ,exchange = @Exchange(value = "ly.sms.exchange",type = "topic"),
            key = {"sms.verify.code"}
    ))
    public void smsListener(Map<String,String> map){
        if(null == map){
            return;
        }
        try {
            smsUtils.sendSms(map.get("phone"),"乐优商城",JSONObject.toJSONString(map),"SMS_151575279");
            log.info("smsUtils.sendSms 注册成功");
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }



}
