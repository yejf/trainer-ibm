package com.kclmedu.mvc.config;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by yejf on 2017/1/11.
 * Spring MVC Configuration
 */
@Configuration  //这是一个配置类
//@Import(value = AppConfig.class)  //读入另一个配置类
@ComponentScan({"com.kclmedu.mvc.controller"})  //指定扫描的包及其子包
@EnableWebMvc //启动WEB MVC 功能 (和 AOP, DT 一样，默认都是禁用的)
public class WebMvcConfig implements WebMvcConfigurer { //spring5.x后直接实现接口

    //日志信息
    private static final Logger LOGGER = Logger.getLogger(WebMvcConfig.class);

    public WebMvcConfig() {
        LOGGER.debug("--- 初始化WebMvcConfig实例...");
    }

    /*************************
     * 注册哪些资源不需要由前置控制器【分发器】来处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                    .addResourceLocations("/static/");
    }
}
