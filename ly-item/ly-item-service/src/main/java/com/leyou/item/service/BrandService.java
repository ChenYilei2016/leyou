package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.exception.LyException;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/04-9:47
 * hello everyone
 */
@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandByPageAndSort(Integer page, Integer rows,
                                                     String sortBy, Boolean desc, String key) {
        PageHelper.startPage(page,rows);
        Example example = new Example(Brand.class);

        if(!StringUtils.isEmpty(sortBy)){
            String orderBy = sortBy+(desc?" DESC":" ASC");
            example.setOrderByClause(orderBy);
        }

        if(!StringUtils.isEmpty(key)){
            example.createCriteria().andLike("name","%" + key + "%")
                    .orEqualTo("letter",key);
        }

        List<Brand> brands = brandMapper.selectByExample(example);
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brands);
        return new PageResult<>(brandPageInfo.getTotal(),brandPageInfo.getList());
    }

    /**
     * 保存品牌 和 分类的列表
     * @param brand
     * @param ids
     */
    public void save(Brand brand, List<Long> ids) {

        int brandId = this.brandMapper.insert(brand);

        ids.forEach( (categoryId)->{
            brandMapper.insertCategoryIdBrandId(brandId,categoryId);
        } );
    }

    public Brand queryByBid(Long bid){
        Brand brand = new Brand();
        brand.setId(bid);
        return brandMapper.selectOne(brand);
    }

    public List<Brand> queryBrandByCategoryId(Long cid) {
        List<Brand> list = brandMapper.queryBrandByCategoryId(cid);

        if(list.size() == 0){
            throw new LyException("queryBrandByCategoryId 查询为0");
        }
        return list;
    }

    public List<Brand> queryBrandsByIds(List<Long> ids) {
        return brandMapper.selectByIdList(ids);
    }
}
