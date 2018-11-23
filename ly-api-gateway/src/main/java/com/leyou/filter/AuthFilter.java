package com.leyou.filter;

import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.utils.CookieUtils;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/22-21:06
 * hello everyone
 */

@Component
@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class AuthFilter extends ZuulFilter {

    @Autowired
    JwtProperties jwtProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        //有些不过滤
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();
        log.warn("uri :{},servletPath:{}",uri,request.getServletPath());

        List<String> filters = jwtProperties.getFilter();
        //如果路径在其中 就放行 -->返回false
        for (String filter : filters) {
            if( uri.startsWith(filter)){
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();

        String token = CookieUtils.getCookieValue(request, jwtProperties.getCookieName());

        try {
            UserInfo userInfo = JwtUtils.getInfoFromToken(token, jwtProperties.getPublicKey());
            //TODO :权限

        } catch (Exception e) {
            //未登陆
            ctx.setResponseStatusCode(403);
            ctx.setSendZuulResponse(false);
        }

        return null;
    }
}
