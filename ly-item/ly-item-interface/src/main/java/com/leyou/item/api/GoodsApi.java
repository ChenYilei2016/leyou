package com.leyou.item.api;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.CartDto;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/11-20:09
 * hello everyone
 */

public interface GoodsApi {
    //查询 spu列表
    @GetMapping("/spu/page")
    public PageResult<Spu> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", defaultValue = "") Boolean saleable);

    //根据spuid 查 spuDetail
    @GetMapping("/spu/detail/{id}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("id") Long id) ;

    //根据spuid 查询所有的 sku对象 并且附上 stock库存量
    @GetMapping("/sku/list")
    public List<Sku> querySkuBySpuId(@RequestParam("id") Long id) ;

    /**
     * 根据spu的id查询spu
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    Spu querySpuById(@PathVariable("id") Long id);

    @GetMapping("/sku/list/ids")
    public List<Sku> querySkusByIds(@RequestParam("ids")List<Long> ids);

    @PostMapping("/stock/decrease")
    public void stockDecrease(
            @RequestBody List<CartDto> carts);
}
