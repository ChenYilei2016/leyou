package com.leyou.common.exception;

/**
 * @author chenyilei
 * @date 2018/11/06-18:26
 * hello everyone
 */
public class LyException extends RuntimeException {

    int code;

    public LyException(LyExceptionEnum lyExceptionEnum){
        super(lyExceptionEnum.getMsg());
        this.code = lyExceptionEnum.getCode();
    }

    public LyException(String message) {
        super(message);
    }
}
