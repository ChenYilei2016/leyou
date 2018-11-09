package com.leyou.item.controller;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/08-20:16
 * hello everyone
 */
@RestController
@RequestMapping("/spec")
public class SpecificationController {
    @Autowired
    SpecificationService specificationService;

    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid")Long cid){
        return ResponseEntity.ok( specificationService.queryGroupsByCid(cid));
    }

    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> queryParamsByGroupId(@RequestParam("gid") Long gid){
        return ResponseEntity.ok(specificationService.queryParamsByGid(gid));
    }
}
