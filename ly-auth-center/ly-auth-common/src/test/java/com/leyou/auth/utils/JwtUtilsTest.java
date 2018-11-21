package com.leyou.auth.utils;

import com.leyou.auth.bean.UserInfo;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.*;

/**
 * @author chenyilei
 * @date 2018/11/21-19:10
 * hello everyone
 */
public class JwtUtilsTest {
    private static final String pubKeyPath = "C:\\Users\\chenyilei\\Desktop\\SteamAnd Vm\\rsa.pub";

    private static final String priKeyPath = "C:\\Users\\chenyilei\\Desktop\\SteamAnd Vm\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    //加密
    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU0Mjc5OTUzMn0.RWBYlFNjfwmzPcW5OyCcFFDBjKb5aPWIjA2a-EkUUjykY0Ta-bFKcqrqdKyUK4rXD8v19PWX3kZmultYGab8D1dOd2MmsEmk_PDjrzWmGgV7a8UiSCfSbz8H6qc8PdPY2N8bQFzSGc5kliqzmjjdsvsrAYKEvmlNBmuqzH2H0Ac".trim();

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }


}