package com.cody.codystage.aop;

import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.kafka.KafkaSender;
import com.cody.codystage.utils.DateUtil;
import com.cody.codystage.common.constants.Constants;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private KafkaSender<JSONObject> kafkaSender;

    @ExceptionHandler(Exception.class)
    public Map<String,Object> handleException(Exception e){
        //错误信息打入kafka
        JSONObject errorJson=new JSONObject();
        JSONObject logJson=new JSONObject();
        logJson.put("request_time", DateUtil.getCurrentDateStr());
        logJson.put("error_info",e);
        errorJson.put("request_error",logJson);
//        kafkaSender.send(errorJson);

        Map<String,Object> resMap= Maps.newHashMap();
        resMap.put("code", Constants.HTTP_RES_CODE_500);
        resMap.put("msg",Constants.HTTP_RES_CODE_500_VALUE);

        log.info("全局捕获异常",e);
        return resMap;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Map<String, Object> handle(WebExchangeBindException exception) {
        //获取参数校验错误集合
        List<FieldError> fieldErrors = exception.getFieldErrors();
        //格式化以提供友好的错误提示
        String data = String.format("参数校验错误（%s）：%s", fieldErrors.size(),
                fieldErrors.stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(";")));
        //参数校验失败响应失败个数及原因
        return ImmutableMap.of("code", exception.getStatus().value(),
                "message", exception.getStatus(),
                "data", data);
    }
}
