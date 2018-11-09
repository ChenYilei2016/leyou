package com.leyou.common.exception;

import lombok.Getter;

/**
 * @author chenyilei
 * @date 2018/11/06-18:27
 * hello everyone
 */
@Getter
public enum LyExceptionEnum {

    BRAND_NOT_FOUND(404,"品牌不存在"),
    FILE_UPLOAD_FAILED(400,"文件上传失败"),
    SPEC_GROUP_NOT_FOUND(404,"规格没有")

    ;

    LyExceptionEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private int code ;
    private String msg;
}
