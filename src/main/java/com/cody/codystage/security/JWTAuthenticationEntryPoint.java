package com.cody.codystage.security;

import com.cody.codystage.common.base.BaseApiService;
import com.cody.codystage.common.constants.ResConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname JWTAuthenticationEntryPoint
 * @Description TODO
 * @Date 2019/4/18 23:12
 * @Created by ZQ
 */
public class JWTAuthenticationEntryPoint extends BaseApiService<Object> implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String reason =authException.getMessage();
        // 未授权统一处理
        response.getWriter().print(setResultError(ResConstants.HTTP_RES_CODE_401,ResConstants.HTTP_RES_CODE_401_VALUE));
    }
}
