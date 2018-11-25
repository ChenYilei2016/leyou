package com.leyou.order.interceptor;

import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.utils.CookieUtils;
import com.leyou.auth.utils.JwtUtils;

import com.leyou.order.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenyilei
 * @date 2018/11/23-15:43
 * hello everyone
 */

// 使用request域 不推荐
//@Override
//public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
//        try {
//        UserInfo user = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
//        //传递用户信息
//        request.setAttribute("user",user);
//        }catch (Exception e){
//        //解析失败
//        log.error("[购物车:LoginInterceptor]购物车解析token失败",e);
//        return false;
//        }
//        return true;
//        }

@Slf4j
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor implements HandlerInterceptor {

    JwtProperties jwtProperties;

    public static final ThreadLocal<UserInfo> local = new ThreadLocal<UserInfo>();

    public LoginInterceptor(@Autowired JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());
        try {
            UserInfo user = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            //传递用户信息
            log.info("preHandle 验证成功 user:{}",user);
            //request.setAttribute("user",user);
            local.set(user);
        }catch (Exception e){
            //解析失败
            log.error("[购物车:LoginInterceptor]购物车解析token失败",e);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        local.remove();
    }
}
