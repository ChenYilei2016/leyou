package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenyilei
 * @date 2018/11/09-10:52
 * hello everyone
 */
@Data
@Table(name = "tb_spu_detail")
public class SpuDetail {

    @Id
    private Long spuId;

    private String description;

    private String specialSpec ; //特殊属性的规格

    private String genericSpec;

    private String packingList;

    private String afterService;

}
