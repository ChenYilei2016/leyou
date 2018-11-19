package com.leyou.sms.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.leyou.sms.bean.SmsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author chenyilei
 * @date 2018/11/19-16:37
 * hello everyone
 */

@EnableConfigurationProperties(value = SmsProperties.class)
@Component
@Slf4j
public class SmsUtils {

    @Autowired
    private SmsProperties prop;

    @Autowired
    private RedisTemplate redisTemplate;

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    static final String KEY_PREFIX = "sms:prefix:";

    public static final long TIME_ISOLATION = 61000; //一分钟

    public SmsUtils(){

    }

    public SendSmsResponse sendSms(String phone, String signName,String templateParam, String template) throws ClientException {

        //查询 限流
        Object last = redisTemplate.opsForValue().get(KEY_PREFIX + phone);
        if( ! ObjectUtils.isEmpty(last)){
            log.error("[发送短信]在限流时间内重复发送,发送失败.");
            return null;
        }

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",prop.getAccessKeyId() , prop.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(template);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(templateParam);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("123456");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        log.info("发送短信状态：{}", sendSmsResponse.getCode());
        log.info("发送短信消息：{}", sendSmsResponse.getMessage());

        //发送成功 限制时间
        if("OK".equals(sendSmsResponse.getCode())){
            log.info("喵喵喵");
            redisTemplate.opsForValue().set(KEY_PREFIX+phone,phone,TIME_ISOLATION,TimeUnit.MILLISECONDS);
        }
        return sendSmsResponse;
    }


}
