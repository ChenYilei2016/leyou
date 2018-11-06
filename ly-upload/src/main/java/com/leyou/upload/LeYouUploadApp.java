package com.leyou.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author chenyilei
 * @date 2018/11/06-17:35
 * hello everyone
 */
@SpringBootApplication
@EnableEurekaClient
public class LeYouUploadApp {
    public static void main(String[] args) {
        //
        SpringApplication.run(LeYouUploadApp.class,args);
    }

}
