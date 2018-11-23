package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenyilei
 * @date 2018/11/22-18:03
 * hello everyone
 */

public interface UserApi {
    @GetMapping("/query")
    public User queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) ;
}
