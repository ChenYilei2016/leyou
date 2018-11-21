package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author chenyilei
 * @date 2018/11/21-19:46
 * hello everyone
 */
@Data
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {
    private String secret; // 密钥

    private String pubKeyPath;// 公钥

    private String priKeyPath;// 私钥

    private int expire;// token过期时间


    private PublicKey publicKey; // 公钥
    private PrivateKey privateKey; // 私钥

    @PostConstruct
    public void init() throws Exception {
        File pubKey = new File(pubKeyPath);
        File priKey = new File(priKeyPath);
        if (!pubKey.exists() || !priKey.exists()) {
            // 生成公钥和私钥
            RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
        }
        // 获取公钥和私钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }
}
