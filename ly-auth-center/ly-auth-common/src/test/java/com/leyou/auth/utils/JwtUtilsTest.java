package com.leyou.auth.utils;

import com.leyou.auth.bean.UserInfo;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
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
    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
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
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU0Mjg4OTY3MH0.hvMzj9DpGW09KIEMQV7XkodV56pUFqwcp8MqTkL53PYOadD5uQDklBwkeFRhtlLA39Xpr5jQvgbYP2CRtQRkYjQyfiWxwLGHD5LBu_5R9-M4fV40kPCyqHSwe6U-TBXf9LRUB10aWB-ISYv0kq9-P9xqSXoHwRgy5hCMyEelaIc".trim();

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }




    public static final String getDomainName(String url) {
        String domainName = null;

        String serverName = url;

            serverName = serverName.toLowerCase();
            serverName = serverName.substring(7);
            final int end = serverName.indexOf("/");
            //serverName = serverName.substring(0, end);
            final String[] domains = serverName.split("\\.");
            int len = domains.length;
            if (len > 3) {
                // www.xxx.com.cn
                domainName = domains[len - 3] + "." + domains[len - 2] + "." + domains[len - 1];
            } else if (len <= 3 && len > 1) {
                // xxx.com or xxx.cn
                domainName = domains[len - 2] + "." + domains[len - 1];
            } else {
                domainName = serverName;
            }


        if (domainName != null && domainName.indexOf(":") > 0) {
            String[] ary = domainName.split("\\:");
            domainName = ary[0];
        }
        return domainName;
    }
    @Test
    public void cookied(){

        System.out.println( getDomainName("http://www.baidu.com") );//baidu.com

    }


}