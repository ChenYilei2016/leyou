package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/12-15:47
 * hello everyone
 */
@RequestMapping("/spec")
public interface SpecificationApi {
    @GetMapping("/params")
    public List<SpecParam>queryParams(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching,
            @RequestParam(value="generic", required = false) Boolean generic);


    @GetMapping("/group")
    public List<SpecGroup> querySpecsGroupsByCid(@RequestParam("cid") Long cid);
}
