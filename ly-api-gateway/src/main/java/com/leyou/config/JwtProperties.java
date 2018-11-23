package com.leyou.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/21-19:46
 * hello everyone
 */
@Data
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {
    private String pubKeyPath;// 公钥
    private PublicKey publicKey; // 公钥
    private String cookieName;
    private List<String> filter;

    @PostConstruct
    public void init() throws Exception {
        File pubKey = new File(pubKeyPath);
        // 获取公钥和私钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);

    }
}
