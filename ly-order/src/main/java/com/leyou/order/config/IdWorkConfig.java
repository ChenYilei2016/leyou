package com.leyou.order.config;

import com.leyou.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chenyilei
 * @date 2018/11/24-15:06
 * hello everyone
 */
@Configuration
@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkConfig {
    @Autowired
    IdWorkerProperties idWorkerProperties;

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(idWorkerProperties.getWorkerId(),idWorkerProperties.getDataCenterId());
    }
}
