package com.example.springboothandlerinterceptor.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @description: 配置拦截器
 * @author: Administrator
 * @create: 2019-07-06 18:23
 **/
//@Configuration
public class MyInterceptorConfig  extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //配置拦截所有请求
        registry.addInterceptor(new MyHandlerInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/static/");
        super.addResourceHandlers(registry);
    }
}
