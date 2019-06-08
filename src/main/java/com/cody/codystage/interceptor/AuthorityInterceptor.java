package com.cody.codystage.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright(c) 2019</p>
 * <p>Email: 1012872209@qq.com</p>
 *
 * @author ZQ
 * @date 2019/6/7:15:01
 */
@Component
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("Authorization") == null) {
            log.info("token拦截url={}",request.getRequestURL());
            JSONObject json = new JSONObject();
            json.put("msg","no Authorization");
            json.put("code",403);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(403);
            try (PrintWriter writer = response.getWriter()) {
                writer.print(json);
            }
            return false;
        }
        return true;
    }
}
