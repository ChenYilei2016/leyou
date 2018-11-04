package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author chenyilei
 * @date 2018/10/30-19:22
 * hello everyone
 */

@EnableEurekaServer
@SpringBootApplication
public class LyRegistry {

    public static void main(String[] args) {
        SpringApplication.run(LyRegistry.class,args);
    }
}
