package com.leyou.search.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * @author chenyilei
 * @date 2018/11/12-15:52
 * hello everyone
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {
    @Autowired
    CategoryClient categoryClient;

    @Test
    public void byIds(){
        categoryClient.queryCategoryByIds(Arrays.asList(1L,2L,3L))
        .forEach(System.out::println);

        //结果
//        Category(id=1, name=图书、音像、电子书刊, parentId=0, isParent=true, sort=1)
//        Category(id=2, name=电子书刊, parentId=1, isParent=true, sort=1)
//        Category(id=3, name=电子书, parentId=2, isParent=false, sort=1)
    }


}
