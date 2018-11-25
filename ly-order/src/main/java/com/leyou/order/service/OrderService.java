package com.leyou.order.service;

import com.github.wxpay.sdk.WXPayUtil;
import com.leyou.auth.bean.UserInfo;
import com.leyou.common.utils.IdWorker;
import com.leyou.item.pojo.CartDto;
import com.leyou.item.pojo.OrderDto;
import com.leyou.item.pojo.Sku;
import com.leyou.order.client.GoodsClient;
import com.leyou.order.enums.OrderStatusEnum;
import com.leyou.order.interceptor.LoginInterceptor;
import com.leyou.order.mapper.OrderDetailMapper;
import com.leyou.order.mapper.OrderMapper;
import com.leyou.order.mapper.OrderStatusMapper;
import com.leyou.order.pojo.Address;
import com.leyou.order.pojo.Order;
import com.leyou.order.pojo.OrderDetail;
import com.leyou.order.pojo.OrderStatus;
import com.leyou.order.utils.PayHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.wxpay.sdk.WXPayConstants.FAIL;

/**
 * @author chenyilei
 * @date 2018/11/24-10:53
 * hello everyone
 */
//订单号 用户数据 商品数据


//减库存
@Service
@Slf4j
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    OrderStatusMapper orderStatusMapper;

    @Autowired
    IdWorker idWorker;

    @Autowired
    GoodsClient goodsClient;
    @Autowired
    PayHelper payHelper;

    @Transactional
    public Long createOrder(OrderDto orderDto) {
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderStatus orderStatus = new OrderStatus();

        //用户登陆账号
        UserInfo user = LoginInterceptor.local.get();
        //1 新增订单
            // 订单的信息
        long orderId = idWorker.nextId();
        order.setOrderId(orderId);
        order.setCreateTime(new Date());
        order.setPaymentType(orderDto.getPaymentType());
            // 用户的信息
        order.setUserId(user.getId());
        order.setBuyerMessage("");
        order.setBuyerRate(false);//无评价
        order.setBuyerNick(user.getUsername());
            // 收货人信息
        Address address = new Address();//假数据 ,可改
        order.setReceiver(address.getName());//收货人
        order.setReceiverAddress(address.getAddress());//收货地址
        order.setReceiverCity(address.getCity());//城
        order.setReceiverDistrict(address.getDistrict());// 区
        order.setReceiverMobile(address.getPhone());//手机
        order.setReceiverState(address.getState());//省
        order.setReceiverZip(address.getZipCode());//邮编
            // 金额
        List<CartDto> carts = orderDto.getCarts();
        Map<Long, Integer> numMap = carts.stream().collect(Collectors.toMap(x -> x.getSkuId(), y -> y.getNum()));
        List<Long> skuIds = carts.stream().map(x -> x.getSkuId()).collect(Collectors.toList());
                //根据 skuids 批量查询sku
        List<Sku> skus = goodsClient.querySkusByIds(skuIds);
        log.warn("根据 skuids 批量查询skus:{}",skus);
        long total = 0L;
        for (Sku sku : skus) {
            //计算商品总价
            total+= sku.getPrice()* numMap.get(sku.getId());

            //构造订单详情的列表 orderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setImage(StringUtils.substringBefore(sku.getImages(),","));
            orderDetail.setNum( numMap.get(sku.getId()) );
            orderDetail.setOwnSpec(sku.getOwnSpec());
            orderDetail.setPrice(sku.getPrice());
            orderDetail.setSkuId(sku.getId());
            orderDetail.setTitle(sku.getTitle());
            orderDetail.setOrderId(order.getOrderId());
            orderDetails.add(orderDetail);
        }
        order.setTotalPay(total); //假设就这个价
        order.setActualPay(total );
            //导入库中
        log.warn("order 导入库前的order:{}",order);
        orderMapper.insertSelective(order);
        //2 订单详情
        log.warn("orderdetail 导入库前的值:{}",orderDetails);
        orderDetailMapper.insertList(orderDetails);
        //3 订单状态
        orderStatus.setCreateTime(new Date());
        orderStatus.setOrderId(order.getOrderId());
        orderStatus.setStatus(OrderStatusEnum.INIT_TIME.getCode());
        log.warn("orderStatus 导入库前的值:{}",orderStatus);
        orderStatusMapper.insertSelective(orderStatus);
        //4 减少库存
            //商品微服务 减少库存 一堆的skuid -- sku买的数量
        log.warn("减少库存前的carts状态{}",carts);
        goodsClient.stockDecrease(carts);

        return order.getOrderId();
    }

    public Order queryOrderById(Long orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);

        //详情
        OrderDetail varSelect = new OrderDetail();
        varSelect.setOrderId(orderId);
        List<OrderDetail> select = orderDetailMapper.select(varSelect);
        order.setOrderDetails(select);
        //状态
        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
        order.setStatus(orderStatus);

        return order;
    }

    //统一下单 生成二维码
    public String createPayUrl(Long orderId) {
        //获取订单的价格
        Order order = queryOrderById(orderId);

        if(OrderStatusEnum.INIT_TIME.getCode() != order.getStatus().getStatus()){
            throw new RuntimeException("订单状态不正确");
        }

        String title = order.getOrderDetails().get(0).getTitle();
        return payHelper.createOrder(orderId,1L,title); //TODO: 测试1分钱
    }

    // 微信给的支付回调
    public void handleNotify(Map<String,String> result) {
        //返回的结果正确验证
        if(false == payHelper.isSuccess(result)){
            throw new RuntimeException("结果不对");
        }
        //签名的验证
        if(false == payHelper.isSignOk(result) ){
            throw new RuntimeException("签名不对");
        }
        //校验金额
        String total_fee = result.get("total_fee"); //返回的价格
        String out_trade_no = result.get("out_trade_no");//我们商户定义的订单号

        Order order = orderMapper.selectByPrimaryKey(out_trade_no);
        if( ! "1".equals(total_fee)  ) {//TODO : 1分钱假数据
            throw new RuntimeException("金额不对");
        }

        OrderStatus orderStatus =new OrderStatus();
        orderStatus.setOrderId(order.getOrderId());
        orderStatus.setStatus(OrderStatusEnum.HAS_PAY.getCode());
        orderStatus.setPaymentTime(new Date());
        int i = orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
        if(1 != i){
            throw new RuntimeException("更新状态失败");
        }
    }
//     * 0 未支付
//     * 1 已支付
//     * 2 支付失败
    public Integer queryStatu(Long id) {

        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(id);
        if( ! OrderStatusEnum.INIT_TIME.equals(orderStatus.getStatus()) ){
            return 1;
        }
        return payHelper.queryPayStatu(id);
    }
}
