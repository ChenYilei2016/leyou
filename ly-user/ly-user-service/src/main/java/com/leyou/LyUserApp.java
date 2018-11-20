package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author chenyilei
 * @date 2018/11/19-20:36
 * hello everyone
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(value = "com.leyou.user.mapper")
public class LyUserApp {
    public static void main(String[] args) {
        SpringApplication.run(LyUserApp.class,args);
    }
}
