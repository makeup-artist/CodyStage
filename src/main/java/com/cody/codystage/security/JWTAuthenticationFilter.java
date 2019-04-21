package com.cody.codystage.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cody.codystage.common.constants.AuthConstants;
import com.cody.codystage.dto.input.UserLoginDTO;
import com.cody.codystage.entity.User;
import com.cody.codystage.exception.ServiceException;
import com.cody.codystage.service.UserService;
import com.cody.codystage.utils.JwtTokenUtil;
import com.cody.codystage.utils.RedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @Classname JWTAuthenticationFilter
 * @Description 获取用户登录的信息
 * @Date 2019/4/18 22:25
 * @Created by ZQ
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/user/login");
    }

    // 从输入流中获取到登录的信息
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDTO loginUser = new ObjectMapper().readValue(request.getInputStream(), UserLoginDTO.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String role = "";
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), role, true);
        response.setHeader("token", AuthConstants.TOKEN_PREFIX + token);
    }


    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        throw new ServiceException(HttpServletResponse.SC_FORBIDDEN, "token验证失败");
    }
}
