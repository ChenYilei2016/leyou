package com.leyou.search.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.MyTest2;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenyilei
 * @date 2018/11/15-13:55
 * hello everyone
 */

@RestController
public class SearchController {
    @Autowired
    SearchService searchService;

    @Autowired
    MyTest2 myTest2;

    @PostMapping("/page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest searchRequest){
        PageResult<Goods> search = searchService.search(searchRequest);
        return ResponseEntity.ok( search );
    }

    @GetMapping("/inData")
    public void inData(){
        myTest2.loadData();
    }
}
