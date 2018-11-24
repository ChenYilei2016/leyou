package com.leyou.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenyilei
 * @date 2018/11/24-10:48
 * hello everyone
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long skuId;
    private Integer num;
}
