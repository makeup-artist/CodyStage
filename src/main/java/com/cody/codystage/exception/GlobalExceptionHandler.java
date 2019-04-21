package com.cody.codystage.exception;

import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.base.BaseResponse;
import com.cody.codystage.common.kafka.KafkaSender;
import com.cody.codystage.utils.DateUtil;
import com.cody.codystage.common.constants.ResConstants;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends BaseApiService<JSONObject> {
    @Autowired
    private KafkaSender<JSONObject> kafkaSender;

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public BaseResponse<JSONObject> handleServiceException(ServiceException e) {
        return setResultError(e.getErrCode(), e.getErrMsg());
    }

    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(Exception e) {
        //错误信息打入kafka
        JSONObject errorJson = new JSONObject();
        JSONObject logJson = new JSONObject();
        logJson.put("request_time", DateUtil.getCurrentDateStr());
        logJson.put("error_info", e);
        errorJson.put("request_error", logJson);
//        kafkaSender.send(errorJson);

        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("code", ResConstants.HTTP_RES_CODE_500);
        resMap.put("msg", ResConstants.HTTP_RES_CODE_500_VALUE);

        log.info("全局捕获异常", e);
        return resMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<JSONObject> validationBodyException(MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p -> {
                FieldError fieldError = (FieldError) p;
                log.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                        "},errorMessage{" + fieldError.getDefaultMessage() + "}");
            });

        }
        return setResultError(HttpServletResponse.SC_BAD_REQUEST, "参数错误");
    }


    @ExceptionHandler(HttpMessageConversionException.class)
    public BaseResponse<JSONObject> parameterTypeException(HttpMessageConversionException exception) {
        log.error(exception.getCause().getLocalizedMessage());
        return setResultError(HttpServletResponse.SC_BAD_REQUEST, "类型转换错误");
    }

}
