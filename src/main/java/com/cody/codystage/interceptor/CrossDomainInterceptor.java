package com.cody.codystage.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CrossDomainInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, content-type, accept, origin, authorization, x-csrftoken");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        return true;
    }
}
