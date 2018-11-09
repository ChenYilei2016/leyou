package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @author chenyilei
 * @date 2018/11/09-8:58
 * hello everyone
 */

@Data
@Table(name = "tb_spec_param")
public class SpecParam {
    private Long id;

    private Long cid;

    private Long groupId;

    private String name;

    @Column(name = "`numeric`")
    private Boolean numeric;

    private String unit;

    private boolean generic;

    private boolean searching;

    private String segments;
}
