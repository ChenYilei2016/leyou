package com.leyou.sms.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chenyilei
 * @date 2018/11/19-16:33
 * hello everyone
 */
@ConfigurationProperties(prefix = "ly.sms")
@Data
public class SmsProperties {
    String accessKeyId;

    String accessKeySecret;

    String signName;

    String verifyCodeTemplate;

}
