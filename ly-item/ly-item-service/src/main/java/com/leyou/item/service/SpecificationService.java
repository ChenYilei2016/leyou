package com.leyou.item.service;

import com.leyou.common.exception.LyException;
import com.leyou.common.exception.LyExceptionEnum;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/08-20:09
 * hello everyone
 */
@Service
@Slf4j
public class SpecificationService {
    @Autowired
    SpecGroupMapper specGroupMapper;

    @Autowired
    SpecParamMapper specParamMapper;

    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> select = specGroupMapper.select(specGroup);

        if(CollectionUtils.isEmpty(select)){
            throw new LyException(LyExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }

        return select;
    }

    public List<SpecParam> queryParamsByGid(Long gid) {
        SpecParam specParam =new SpecParam();
        specParam.setGroupId(gid);
        List<SpecParam> select = specParamMapper.select(specParam);
        if(CollectionUtils.isEmpty(select)){
            throw new LyException("param 为空");
        }
        return select;
    }
}
