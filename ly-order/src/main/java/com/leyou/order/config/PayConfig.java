package com.leyou.order.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;

import java.io.InputStream;

/**
 * @author chenyilei
 * @date 2018/11/24-20:42
 * hello everyone
 */
@Data
public class PayConfig implements WXPayConfig {
   private String appID ="wx8397f8696b538317"; //公众账号ID
   private String mchID="1473426802";//商户号
   private String key="T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"; //签名
   private int httpConnectTimeoutMs = 1000;
   private int httpReadTimeoutMs = 5000;
   private String notifyUrl = "http://fzep2d.natappfree.cc/notify";

    @Override
    public InputStream getCertStream() {
        return null;
    }
}
