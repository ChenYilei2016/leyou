package com.leyou.order.dto;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.leyou.order.config.PayConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/11/24-20:13
 * hello everyone
 */
public class Demo {
    PayConfig config = new PayConfig();

    WXPay wxPay;
    {
        wxPay = new WXPay(config,WXPayConstants.SignType.HMACSHA256);
    }

    public static void main(String[] args) {
        String url = new Demo().createOrder(34124148L,1L,"商城测试");
        System.out.println(url);
    }

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
            for (Map.Entry<String, String> entry : result.entrySet()) {
                String key = entry.getKey();
                System.out.println(key+" : "+entry.getValue());
            }
            System.out.println("-------------");
            String url = result.get("code_url");
            return url;
        }catch (Exception e){

        }

        return null;
    }
}
