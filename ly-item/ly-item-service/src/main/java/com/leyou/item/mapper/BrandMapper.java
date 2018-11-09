package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author chenyilei
 * @date 2018/11/04-14:29
 * hello everyone
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand (category_id,brand_id) values(#{brandId},#{categoryId} )")
    void insertCategoryIdBrandId(@Param("brandId") int brandId, @Param("categoryId") Long categoryId);
}
