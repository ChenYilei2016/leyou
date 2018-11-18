package com.leyou.page.mq.listener;

import com.leyou.page.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author chenyilei
 * @date 2018/11/18-15:28
 * hello everyone
 */

@Component
public class ItemListener {

    @Autowired
    PageService pageService;

    @RabbitListener(bindings =@QueueBinding(
            value = @Queue(value = "page.item.save.queue"),
            exchange = @Exchange(name = "ly.item.exchange",type =ExchangeTypes.TOPIC),
            key = {"item.save"}
    ))
    public void Spusave(Long id){

    }

}
