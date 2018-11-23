package com.leyou.user.controller;

import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import com.leyou.user.utils.CodecUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author chenyilei
 * @date 2018/11/22-18:25
 * hello everyone
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @org.junit.Test
    public void register() {
        User user = new User();
        user.setCreated(new Date());

        user.setUsername("root");
        user.setPassword("root");

        String salt = CodecUtils.generateSalt();
        String pwd = CodecUtils.md5Hex(user.getPassword(),salt);

        user.setPassword(pwd);
        user.setSalt(salt);
        user.setCreated(new Date());
        userMapper.insert(user);
    }
}