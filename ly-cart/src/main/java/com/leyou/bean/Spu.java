package com.leyou.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author chenyilei
 * @date 2018/11/09-10:52
 * hello everyone
 */
@Data
public class Spu {
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


    private String bname;

    private String cname;

    private List<String> skus;

}
