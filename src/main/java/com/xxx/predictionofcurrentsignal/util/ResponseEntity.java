package com.xxx.predictionofcurrentsignal.util;

import java.io.Serializable;

public class ResponseEntity<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public ResponseEntity() {

    }

    public static ResponseEntity success() {
        ResponseEntity restResponse = new ResponseEntity();
        restResponse.setResultCode(ResultCode.SUCCESS);

        return restResponse;
    }

    public static <T>ResponseEntity success(T data) {
        ResponseEntity restResponse = new ResponseEntity();
        restResponse.setResultCode(ResultCode.SUCCESS);
        restResponse.setData(data);
        return restResponse;
    }

    public static ResponseEntity fail() {
        ResponseEntity restResponse = new ResponseEntity();
        restResponse.setResultCode(ResultCode.FAIL);

        return restResponse;
    }


    public static ResponseEntity fail(ResultCode resultCode) {
        ResponseEntity restResponse = new ResponseEntity();
        restResponse.setResultCode(resultCode);

        return restResponse;
    }

    public static ResponseEntity fail(String message) {
        ResponseEntity restResponse = new ResponseEntity();
        restResponse.setCode(ResultCode.FAIL.code());
        restResponse.setMessage(message);

        return restResponse;
    }

    public static ResponseEntity fail(Integer code, String message) {
        ResponseEntity restResponse = new ResponseEntity();
        restResponse.setCode(code);
        restResponse.setMessage(message);

        return restResponse;
    }

    public static <T>ResponseEntity fail(ResultCode resultCode, T data) {
        ResponseEntity restResponse = new ResponseEntity();
        restResponse.setResultCode(resultCode);
        restResponse.setData(data);

        return restResponse;
    }

    private void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

