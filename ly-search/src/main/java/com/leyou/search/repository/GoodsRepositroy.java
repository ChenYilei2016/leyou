package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author chenyilei
 * @date 2018/11/12-16:11
 * hello everyone
 */
public interface GoodsRepositroy extends ElasticsearchRepository<Goods,Long> {

}
