package com.leyou.order.utils;

import com.github.wxpay.sdk.WXPay;
import com.leyou.order.config.PayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import static com.github.wxpay.sdk.WXPayConstants.*;
/**
 * @author chenyilei
 * @date 2018/11/24-21:44
 * hello everyone
 */
@Component
@Slf4j
public class PayHelper {
    @Autowired
    WXPay wxPay;

    @Autowired
    PayConfig config;

    public String createOrder(Long orderId,Long totalPay,String desc){
        try {
            Map<String,String> data = new HashMap<>();
            //描述
            data.put("body",desc);
            //订单号
            data.put("out_trade_no",orderId.toString());
            //金额
            data.put("total_fee",totalPay.toString());
            //终端IP
            data.put("spbill_create_ip","127.0.0.1");
            //回调地址 ******
            data.put("notify_url",config.getNotifyUrl());
            //交易类型 扫码
            data.put("trade_type","NATIVE");

            Map<String, String> result = wxPay.unifiedOrder(data);

            String returnCode = result.get("return_code");
            if(FAIL.equals(returnCode)){
                log.error("通信失败:{}",result.get("return_msg"));
                return null;
            }
            String resultCode = result.get("result_code");
            if(FAIL.equals(resultCode)){
                log.error("业务失败:{}",result.get("err_code"),result.get("err_code_des"));
                return null;
            }
            //二维码连接
            //下单成功
            String url = result.get("code_url");
            log.warn("正确url:{}",url);
            return url;
        }catch (Exception e){

        }

        return null;
    }
}
