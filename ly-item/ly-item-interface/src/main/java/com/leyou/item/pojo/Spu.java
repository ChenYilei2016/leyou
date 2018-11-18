package com.leyou.item.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/09-10:52
 * hello everyone
 */
@Data
@Table(name = "tb_spu")
public class Spu {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private String title;

    private String subTitle;
    private Boolean saleable;
    private Boolean valid;

    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date lastUpdateTime;

    @Transient
    private String bname;
    @Transient
    private String cname;
    @Transient
    private SpuDetail spuDetail;
    @Transient
    private List<Sku> skus;

}
