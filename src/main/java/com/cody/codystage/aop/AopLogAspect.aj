package com.cody.codystage.aop;

import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.kafka.KafkaSender;
import com.cody.codystage.utils.DateUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Classname AopLogAspect
 * @Description 收集用户行为打往kafka
 * @Date 2019/4/14 15:49
 * @Created by ZQ
 */
@Aspect
@Component
public class AopLogAspect {
    @Autowired
    private KafkaSender<JSONObject> kafkaSender;

    @Pointcut("execution(* com.cody.codystage.controller.*.*(..))")
    private void serviceAspect(){
    }

    @Before(value = "serviceAspect()")
    public void methodBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("request_time", DateUtil.getCurrentDateStr());
        jsonObject.put("request_url", request.getRequestURL().toString());
        jsonObject.put("request_method", request.getMethod());
        jsonObject.put("signature", joinPoint.getSignature());
        jsonObject.put("request_args", Arrays.toString(joinPoint.getArgs()));

        JSONObject requestJsonObject = new JSONObject();
        requestJsonObject.put("request", jsonObject);
//        kafkaSender.send(requestJsonObject);
    }

    @AfterReturning(returning = "o", pointcut = "serviceAspect()")
    public void methodAfterReturning(Object o) {
        JSONObject respJSONObject = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response_time", DateUtil.getCurrentDateStr());
        jsonObject.put("response_content", JSONObject.toJSONString(o));
        respJSONObject.put("response", jsonObject);

//        kafkaSender.send(respJSONObject);

    }
}
