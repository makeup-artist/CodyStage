package com.cody.codystage.config;


import com.cody.codystage.interceptor.AuthorityInterceptor;
import com.cody.codystage.interceptor.CrossDomainInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private CrossDomainInterceptor crossDomainInterceptor;

    @Resource
    private AuthorityInterceptor authorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossDomainInterceptor).addPathPatterns("/**");
        registry.addInterceptor(authorityInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/csrf","/api/user/userInfo/**","/api/user/check/**","/api/user/code","/api/user/login","/api/user/login/code","/api/user/register/code","/api/user/register/tradition","/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.of(50,DataUnit.MEGABYTES));
        /// 设置总上传数据总大小
        factory.setMaxFileSize(DataSize.of(500,DataUnit.MEGABYTES));
        return factory.createMultipartConfig();
    }
}
