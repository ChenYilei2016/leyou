package com.leyou.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chenyilei
 * @date 2018/11/24-12:34
 * hello everyone
 */

@Data
@ConfigurationProperties(prefix = "ly.worker")
public class IdWorkerProperties {
    private Long workerId;
    private Long dataCenterId;
}
