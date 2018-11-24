package com.leyou.order.pojo;




import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-27 16:33
 * @Feature: 订单类实体
 */
@Data
@Table(name = "tb_order")
public class Order {

    @Id
    private Long orderId;

    /**
     * 总金额
     */
    @NotNull
    private Long totalPay;
    /**
     * 实付金额
     */
    @NotNull
    private Long actualPay;

    /**
     * 支付类型，1、在线支付，2、货到付款
     */
    @NotNull
    private Integer paymentType;

    /**
     * 参与促销活动的id
     */
    private String promotionIds;

    /**
     * 邮费
     */
    private String postFee;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 物流名称
     */
    private String shippingName;

    /**
     * 物流单号
     */
    private String shippingCode;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 买家留言
     */
    private String buyerMessage;

    /**
     * 买家昵称
     */
    private String buyerNick;

    /**
     * 买家是否已经评价
     */
    private Boolean buyerRate;

    /**
     * 收货人全名
     */
    private String receiver;

    /**
     * 移动电话
     */
    private String receiverMobile;

    /**
     * 省份
     */
    private String receiverState;

    /**
     * 城市
     */
    private String receiverCity;

    /**
     *  区/县
     */
    private String receiverDistrict;

    /**
     * 收货地址，如：xx路xx号
     */
    private String receiverAddress;

    /**
     * 邮政编码,如：310001
     */
    private String receiverZip;

    /**
     * 发票类型，0无发票，1普通发票，2电子发票，3增值税发票
     */
    private Integer invoiceType;

    /**
     * 订单来源 1:app端，2：pc端，3：M端，4：微信端，5：手机qq端
     */
    private Integer sourceType;

    @Transient
    private List<OrderDetail> orderDetails;

    @Transient
    private OrderStatus status;


}
