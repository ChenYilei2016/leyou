package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author chenyilei
 * @date 2018/11/22-18:07
 * hello everyone
 */

@FeignClient(value = "user-service")
public interface UserClient extends UserApi {
}
