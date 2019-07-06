package com.example.springboothandlerinterceptor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 配置拦截器
 * @author: Administrator
 * @create: 2019-07-06 18:23
 **/
@Configuration
public class MyInterceptorConfig2 implements WebMvcConfigurer{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截所有请求
        registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/**");

    }

}
