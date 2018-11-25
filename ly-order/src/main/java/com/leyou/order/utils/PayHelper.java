package com.leyou.order.utils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.leyou.order.config.PayConfig;
import com.leyou.order.enums.OrderStatusEnum;
import com.leyou.order.mapper.OrderStatusMapper;
import com.leyou.order.pojo.Order;
import com.leyou.order.pojo.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    @Autowired
    OrderStatusMapper orderStatusMapper;

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

            isSuccess(result);

            //二维码连接
            //下单成功
            String url = result.get("code_url");
            log.warn("正确url:{}",url);
            return url;
        }catch (Exception e){

        }

        return null;
    }

    public boolean isSuccess(Map<String,String> result){
        String returnCode = result.get("return_code");
        if(FAIL.equals(returnCode)){
            log.error("通信失败:{}",result.get("return_msg"));
            return false;
        }
        String resultCode = result.get("result_code");
        if(FAIL.equals(resultCode)){
            log.error("业务失败:{}",result.get("err_code"),result.get("err_code_des"));
            return false;
        }
        return true;
    }

    public boolean isSignOk(Map<String, String> result) {
        try {
            String sign1 = WXPayUtil.generateSignature(result,config.getKey(),SignType.HMACSHA256);
            String sign2 = WXPayUtil.generateSignature(result,config.getKey(),SignType.MD5);
            log.warn("sign1:{},sign2:{}",sign1,sign2);
            String sign = result.get("sign");
            return StringUtils.equals(sign1,sign) || StringUtils.equals(sign2,sign);
        } catch (Exception e) {
            log.error("解析签名错误{}",e);
            e.printStackTrace();
        }
        return false;
    }

    public Integer queryPayStatu(Long id) {
        try {
            Map<String,String> data = new HashMap<>();
            data.put("out_trade_no",id.toString());
            Map<String, String> result = wxPay.orderQuery(data);

            if(false == isSuccess(result)){
                throw new RuntimeException("结果不对");
            }
            //签名的验证
            if(false == isSignOk(result) ){
                throw new RuntimeException("签名不对");
            }

            //校验金额
            String total_fee = result.get("total_fee"); //返回的价格
            String out_trade_no = result.get("out_trade_no");//我们商户定义的订单号
            if( ! "1".equals(total_fee)  ) {//TODO : 1分钱假数据
                throw new RuntimeException("金额不对");
            }
            String statu = result.get("trade_state");
            if(SUCCESS.equals(statu)){
                //订单是成功的
                OrderStatus orderStatus =new OrderStatus();
                orderStatus.setOrderId( Long.parseLong(out_trade_no) );
                orderStatus.setStatus(OrderStatusEnum.HAS_PAY.getCode());
                orderStatus.setPaymentTime(new Date());
                int i = orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
                if(1 != i){
                    throw new RuntimeException("更新状态失败");
                }
                return 1; //成功;
            }
            if("NOTPAY".equals(statu) || "USERPAYING".equals(statu)){
                return 0;
            }
            return 2;
        }catch (Exception e){
            return 0;
        }
    }
}
