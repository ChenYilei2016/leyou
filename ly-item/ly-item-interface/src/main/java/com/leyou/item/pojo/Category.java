package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenyilei
 * @date 2018/11/04-9:39
 * hello everyone
 */
@Table(name="tb_category")
@Data
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;

    private Integer sort;
    // getter和setter略
    // 注意isParent的get和set方法
}
