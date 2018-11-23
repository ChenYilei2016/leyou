package com.leyou.auth.service;

import com.leyou.auth.bean.UserInfo;
import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenyilei
 * @date 2018/11/22-17:26
 * hello everyone
 */

@Service
@Slf4j
public class AuthService {
    @Autowired
    UserClient userClient;

    @Autowired
    JwtProperties prop;

    public String authentication(String username, String password) {
        User user = null;
        String token = null;
        try {
             user= userClient.queryUser(username, password);
             token = JwtUtils.
                    generateToken(new UserInfo(user.getId(), username), prop.getPrivateKey(), prop.getExpire());
        }catch (Exception e){
            if(null == user){
                log.error("登陆校验用户不存在");
            }
            log.error("[登陆校验]获取的user:{},生成的token:{},error:{}",user,token,e.getMessage());
        }
        return token;
    }
}
