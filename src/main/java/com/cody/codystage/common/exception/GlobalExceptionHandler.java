package com.cody.codystage.common.exception;

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
import java.io.IOException;
import java.io.PrintWriter;
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
    public void handleException(HttpServletResponse response, Exception e) {
        //错误信息打入kafka
        JSONObject errorJson = new JSONObject();
        JSONObject logJson = new JSONObject();
        logJson.put("request_time", DateUtil.getCurrentDateStr());
        logJson.put("error_info", e);
        errorJson.put("request_error", logJson);
//        kafkaSender.send(errorJson);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ResConstants.HTTP_RES_CODE_500);
        jsonObject.put("msg", ResConstants.HTTP_RES_CODE_500_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(ResConstants.HTTP_RES_CODE_500);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(jsonObject);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        log.info("全局捕获异常", e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public void validationBodyException(HttpServletResponse response, MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(p -> {
                FieldError fieldError = (FieldError) p;
                log.info("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                        "},errorMessage{" + fieldError.getDefaultMessage() + "}");
            });

        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", ResConstants.HTTP_RES_CODE_400);
        jsonObject.put("msg", "参数类型错误");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(ResConstants.HTTP_RES_CODE_400);
        try (PrintWriter writer = response.getWriter()) {
            writer.print(jsonObject);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
