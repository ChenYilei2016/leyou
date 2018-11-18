package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author chenyilei
 * @date 2018/11/12-15:50
 * hello everyone
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
