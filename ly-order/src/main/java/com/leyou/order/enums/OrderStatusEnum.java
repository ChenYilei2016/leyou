package com.leyou.order.enums;

/**
 * @author chenyilei
 * @date 2018/11/24-13:57
 * hello everyone
 */

import lombok.Getter;

/**
 * 初始阶段：1、未付款、未发货；初始化所有数据
 * 付款阶段：2、已付款、未发货；更改付款时间
 * 发货阶段：3、已发货，未确认；更改发货时间、物流名称、物流单号
 * 成功阶段：4、已确认，未评价；更改交易结束时间
 * 关闭阶段：5、关闭； 更改更新时间，交易关闭时间。
 * 评价阶段：6、已评价
 */
@Getter
public enum  OrderStatusEnum {
    INIT_TIME(1,"初始阶段：1、未付款、未发货；初始化所有数据"),
    HAS_PAY(2,"付款阶段：2、已付款、未发货；更改付款时间"),
    UN_CONFIRM(3,"发货阶段：3、已发货，未确认；更改发货时间、物流名称、物流单号"),
    SUCCESS(4,"成功阶段：4、已确认，未评价；更改交易结束时间"),
    CLOSED(5,"关闭阶段：5、关闭； 更改更新时间，交易关闭时间。"),
    RATED(6,"评价阶段：6、已评价")
    ;
    private int code;
    private String desc;

    OrderStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
