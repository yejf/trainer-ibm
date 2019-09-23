package com.kclmedu.springboot.config;

import com.kclmedu.springboot.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("--  注册拦截器");
        //创建我们自定义的拦截器
        LogInterceptor logInterceptor = new LogInterceptor();
        //
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
    }
}
