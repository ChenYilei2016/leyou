package com.leyou.service;

import com.alibaba.fastjson.JSONObject;
import com.leyou.auth.bean.UserInfo;
import com.leyou.bean.Cart;
import com.leyou.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenyilei
 * @date 2018/11/23-17:45
 * hello everyone
 */

//-redis 存 用户购物车数据
//- 第一层Map，Key是用户id
//- 第二层Map，Key是购物车中商品id，值是购物车数据
//   Map<String,Map<String,String>>
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String KEY_PREFIX = "cart:uid:";

    public void addCart(Cart cart) {
        UserInfo userInfo = LoginInterceptor.local.get();
        String key  = KEY_PREFIX+userInfo.getId();
        String skuKey = cart.getSkuId().toString();
        BoundHashOperations<String, String, String> opt = redisTemplate.boundHashOps(key);

        cart.setUserId(userInfo.getId());
        if(opt.hasKey(skuKey)) {
            //商品有 则+1
            Cart cacheCart = JSONObject.parseObject( (String) opt.get( skuKey ), Cart.class);
            cart.setNum( cart.getNum() +cacheCart.getNum() );
        }else{
            //没有 则新增
        }
        opt.put( skuKey ,JSONObject.toJSONString(cart) );
    }

    public List<Cart> queryCartList() {
        UserInfo userInfo = LoginInterceptor.local.get();
        String key  = KEY_PREFIX+userInfo.getId();

        List<Cart> collect = redisTemplate.opsForHash().entries(key).values().stream().map(x -> {
            return JSONObject.parseObject( (String)x, Cart.class);
        }).collect(Collectors.toList());

        return collect;
    }

    public void updateNum(Long skuId, Integer num) {
        UserInfo userInfo = LoginInterceptor.local.get();
        String key  = KEY_PREFIX+userInfo.getId();
        BoundHashOperations<String, String, String> opt = redisTemplate.boundHashOps(key);

        Cart cart = JSONObject.parseObject(opt.get(skuId), Cart.class);
        cart.setNum( cart.getNum()+num );
        opt.put(skuId.toString(),JSONObject.toJSONString(cart));
    }

    public void deleteCart(Long skuId) {
        UserInfo userInfo = LoginInterceptor.local.get();
        String key  = KEY_PREFIX+userInfo.getId();
        BoundHashOperations<String, String, String> opt = redisTemplate.boundHashOps(key);

        opt.delete(skuId);
    }
}
