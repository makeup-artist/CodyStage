package com.cody.codystage.common.base;


import com.cody.codystage.common.constants.Constants;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BaseApiService<T> {

    // 返回错误，手动传code和msg
    public BaseResponse<T> setResultError(Integer code, String msg) {
        return setResult(code, msg);
    }

    // 返回错误，可以传msg
    public BaseResponse<T> setResultError(String msg) {
        return setResult(Constants.HTTP_RES_CODE_500, msg, null);
    }

    // 返回成功，可以传data值
    public BaseResponse<T> setResultSuccess(T data) {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    // 返回成功，沒有data值
    public BaseResponse<T> setResultSuccess() {
        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    // 返回成功，沒有data值
    public BaseResponse<T> setResultSuccess(String msg) {
        return setResult(Constants.HTTP_RES_CODE_200, msg, null);
    }

    // 通用封装
    public BaseResponse<T> setResult(Integer code, String msg, T data) {
        return new BaseResponse<T>(code, msg, data);
    }
    public BaseResponse<T> setResult(Integer code, String msg) {
        return new BaseResponse<T>(code, msg);
    }

    // 调用数据库层判断
    public Boolean toDaoResult(int result) {
        return result > 0;
    }

    // 接口直接返回true 或者false
    public Boolean isSuccess(BaseResponse<T> baseResponse) {
        if (baseResponse == null) {
            return false;
        }
        if (baseResponse.equals(Constants.HTTP_RES_CODE_500)) {
            return false;
        }
        return true;
    }
}
