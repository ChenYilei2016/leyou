package com.leyou.order.config;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyilei
 * @date 2018/11/24-21:24
 * hello everyone
 */
@Configuration
public class WXPayConfigU {

    @Bean
//    @ConfigurationProperties(prefix = "xxx") //还可以这样哦! 别忘记了
    public PayConfig payConfig(){
        return new PayConfig();
    }

    @Bean
    public WXPay wxPay(@Autowired PayConfig payConfig){
        return new WXPay(payConfig,WXPayConstants.SignType.HMACSHA256);
    }

}
