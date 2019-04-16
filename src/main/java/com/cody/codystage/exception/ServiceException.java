package com.cody.codystage.exception;

import lombok.Data;

import java.util.Arrays;

@Data
public class ServiceException extends RuntimeException {
    private String errCode;
    private Object[] params;
    private String errMsg;

    public ServiceException() {
        super();
    }

    public ServiceException(String errMsg) {
        super(errMsg);
    }

    public ServiceException(String errCode, String errMsg) {
        super("errCode:" + errCode + ";errMsg:" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ServiceException(String errCode, Object[] params, String errMsg) {
        super("errCode:" + errCode + ";params:" + Arrays.toString(params) + ";errMsg:" + errMsg);
        this.errCode = errCode;
        this.params = params;
        this.errMsg = errMsg;
    }

    public ServiceException(String errCode, String errMsg, Throwable throwable) {
        super("errCode:" + errCode + ";errMsg:" + errMsg, throwable);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }
}
