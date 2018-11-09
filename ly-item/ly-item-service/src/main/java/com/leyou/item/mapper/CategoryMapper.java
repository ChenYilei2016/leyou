package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/04-9:46
 * hello everyone
 */

public interface CategoryMapper extends Mapper<Category> {
    List<Category> selecListByBrandId(Long bid);
}
