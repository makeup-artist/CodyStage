package com.cody.codystage.security;

import com.cody.codystage.common.constants.AuthConstants;
import com.cody.codystage.exception.ServiceException;
import com.cody.codystage.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @Classname JWTAuthorizationFilter
 * @Description 鉴权
 * @Date 2019/4/18 23:02
 * @Created by ZQ
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            String tokenHeader = request.getHeader(AuthConstants.TOKEN_HEADER);
            if (tokenHeader == null || !tokenHeader.startsWith(AuthConstants.TOKEN_PREFIX)) {
                chain.doFilter(request, response);
                return;
            }
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
            super.doFilterInternal(request, response, chain);
        } catch (Exception e) {
            throw new ServiceException(HttpServletResponse.SC_FORBIDDEN,"未授权");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(AuthConstants.TOKEN_PREFIX, "");
        String username = JwtTokenUtil.getUsername(token);
        String role = JwtTokenUtil.getUserRole(token);
        if (username != null){
            return new UsernamePasswordAuthenticationToken(username, null,
                    Collections.singleton(new SimpleGrantedAuthority(role))
            );
        }
        return null;
    }
}
