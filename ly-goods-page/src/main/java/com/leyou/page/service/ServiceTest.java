package com.leyou.page.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenyilei
 * @date 2018/11/17-19:51
 * hello everyone
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    PageService pageService;

    @Test
    public void createThtml(){
        Map map =new HashMap();
        pageService.loadModel(map,1L);
        pageService.createHtml(map);
    }

}
