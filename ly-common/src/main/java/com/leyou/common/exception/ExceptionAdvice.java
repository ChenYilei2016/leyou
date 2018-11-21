package com.leyou.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author chenyilei
 * @date 2018/11/20-15:29
 * hello everyone
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = LyException.class)
    public ResponseEntity<String> hand(LyException e){
        return ResponseEntity.status(e.getCode()).body(e.getMessage());
    }
}
