package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author chenyilei
 * @date 2018/11/24-9:48
 * hello everyone
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.leyou.order.mapper")
public class LyOrderApp {
    public static void main(String[] args) {
        SpringApplication.run(LyOrderApp.class,args);
    }
}
