package com.leyou.order.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author chenyilei
 * @date 2018/11/24-13:10
 * hello everyone
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
