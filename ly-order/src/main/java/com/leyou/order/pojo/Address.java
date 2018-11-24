package com.leyou.order.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: 98050
 * @Time: 2018-10-30 23:08
 * @Feature: 收货地址
 */
@Table(name = "tb_address")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 1L;

    /**
     * 用户id
     */
    private Long userId =1L;

    /**
     * 收货人
     */
    private String name="陈先森";

    /**
     * 收货电话
     */
    private String phone="17758163513";

    /**
     * 邮编
     */
    private String zipCode="998889";

    /**
     * 省
     */
    private String state="浙江省";

    /**
     * 市
     */
    private String city="诸暨市";

    /**
     * 区/县
     */
    private String district="诸暨区";

    /**
     * 详细地址
     */
    private String address="暨阳学院";

    /**
     * 是否是默认地址
     */
    private Boolean defaultAddress = true;

    /**
     * 地址标签
     */
    private String label="no";
}
