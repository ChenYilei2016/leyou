package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author chenyilei
 * @date 2018/11/10-10:31
 * hello everyone
 */
public interface StockMapper  extends BaseMapper<Stock> {

    @Update("update tb_stock set stock = stock - #{num} where sku_id = #{id} and stock>=#{num}")
    int decreaseStock(@Param("id") Long id,@Param("num")Integer num);
}
