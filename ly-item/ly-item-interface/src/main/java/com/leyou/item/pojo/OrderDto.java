package com.leyou.item.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/24-10:44
 * hello everyone
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    @NotNull
    private Long addressId;

    @NotNull
    private Integer paymentType;

    @NotNull
    private List<CartDto> carts;
}
