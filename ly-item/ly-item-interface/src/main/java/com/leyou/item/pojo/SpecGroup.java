package com.leyou.item.pojo;

import lombok.Data;
import lombok.Value;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
