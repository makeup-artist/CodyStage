package com.cody.codystage.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class ServiceException extends RuntimeException {
    private Integer errCode;
    private Object[] params;
    private String errMsg;

    public ServiceException() {
        super();
    }

    public ServiceException(String errMsg) {
        super(errMsg);
    }

    public ServiceException(Integer errCode, String errMsg) {
        super("errCode:" + errCode + ";errMsg:" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ServiceException(Integer errCode, Object[] params, String errMsg) {
        super("errCode:" + errCode + ";params:" + Arrays.toString(params) + ";errMsg:" + errMsg);
        this.errCode = errCode;
        this.params = params;
        this.errMsg = errMsg;
    }

    public ServiceException(Integer errCode, String errMsg, Throwable throwable) {
        super("errCode:" + errCode + ";errMsg:" + errMsg, throwable);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public ServiceException(Throwable throwable) {
        super(throwable);
    }
}
