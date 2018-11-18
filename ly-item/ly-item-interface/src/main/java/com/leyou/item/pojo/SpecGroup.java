package com.leyou.item.pojo;

import lombok.Data;
import lombok.Value;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/08-19:51
 * hello everyone
 */

@Data
@Table(name="tb_spec_group")
public class SpecGroup {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long cid;

    private String name;

    @Transient
    private List<SpecParam> params; // 该组下的所有规格参数集合

}
