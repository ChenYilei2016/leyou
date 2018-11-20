package com.leyou.user.controller;

import com.leyou.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * @author chenyilei
 * @date 2018/11/20-12:48
 * hello everyone
 */

@RestController
public class UserController {

    @Autowired
    UserService userService;
    /**
     * 校验数据是否可用
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data,
                                                 @PathVariable(value = "type") Integer type) throws UnsupportedEncodingException {
        Boolean boo = this.userService.checkData(data, type);
        if (boo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    //发送短信注册的验证码
    @PostMapping("/code")
    public ResponseEntity<Void> sendVerifyCode(String phone){
        userService.sendCode(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
