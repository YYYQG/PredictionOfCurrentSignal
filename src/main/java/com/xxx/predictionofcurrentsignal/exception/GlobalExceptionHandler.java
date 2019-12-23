package com.xxx.predictionofcurrentsignal.exception;

import com.xxx.predictionofcurrentsignal.util.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity exceptionHandler(Exception e){
        Map<String,Object> result = new HashMap<String,Object>();
        //正常开发中，可创建一个统一响应实体，如CommonResp
        e.printStackTrace();
        return ResponseEntity.fail(e.getMessage());
    }


}
