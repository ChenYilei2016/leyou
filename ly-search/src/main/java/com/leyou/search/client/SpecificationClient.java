package com.leyou.search.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author chenyilei
 * @date 2018/11/12-15:49
 * hello everyone
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
