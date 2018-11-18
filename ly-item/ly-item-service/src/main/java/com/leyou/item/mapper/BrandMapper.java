package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/04-14:29
 * hello everyone
 */
public interface BrandMapper extends Mapper<Brand>,IdListMapper<Brand,Long> {

    @Insert("insert into tb_category_brand (category_id,brand_id) values(#{brandId},#{categoryId} )")
    void insertCategoryIdBrandId(@Param("brandId") int brandId, @Param("categoryId") Long categoryId);

    @Select("SELECT b.* FROM tb_brand b LEFT JOIN tb_category_brand cb ON b.id = cb.brand_id WHERE cb.category_id = #{cid}")
    List<Brand> queryBrandByCategoryId(Long cid);
}
