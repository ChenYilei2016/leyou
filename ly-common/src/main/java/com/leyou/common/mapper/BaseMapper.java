package com.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;


/**
 * @author chenyilei
 * @date 2018/11/10-11:52
 * hello everyone
 */
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T> ,InsertListMapper<T>,IdListMapper<T,Long> {

}
