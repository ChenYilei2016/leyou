package com.leyou.page.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author chenyilei
 * @date 2018/11/11-20:09
 * hello everyone
 */

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
