package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author chenyilei
 * @date 2018/10/30-19:41
 * hello everyone
 */

@EnableEurekaClient
@SpringBootApplication
@EnableZuulProxy
public class LyApiGateway {

    public static void main(String[] args) {
        SpringApplication.run(LyApiGateway.class,args);
    }
}
