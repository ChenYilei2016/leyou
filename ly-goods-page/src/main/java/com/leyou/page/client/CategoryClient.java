package com.leyou.page.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author chenyilei
 * @date 2018/11/11-19:57
 * hello everyone
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
