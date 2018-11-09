package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/04-9:47
 * hello everyone
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    public List<Category> queryListByParent(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    public List<Category> queryByBrandId(Long bid) {
        return categoryMapper.selecListByBrandId(bid);
    }
}
