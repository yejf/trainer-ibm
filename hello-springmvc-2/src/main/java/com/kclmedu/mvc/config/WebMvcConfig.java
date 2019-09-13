package com.kclmedu.mvc.config;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by yejf on 2017/1/11.
 * Spring MVC Configuration
 */
@Configuration  //这是一个配置类
@Import(value = AppConfig.class)  //读入另一个配置类
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

    /*****************
     * 配置视图解析器
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver =
                            new InternalResourceViewResolver();
        //设置前缀
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        //配置视图解析引擎
        viewResolver.setViewClass(JstlView.class); //默认就是JSP视图解析
        //
        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        //做一些设置
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(20*1024*1024);
        multipartResolver.setMaxUploadSizePerFile(2*1024*1024); //单个文件不能超过2M
        return multipartResolver;
    }

    /***********
     * 用于i18n 国际化处理，非必须
     * 注：此处的方法名最好叫 messageSource,否则，单独测试这个Bean时可能会出现找到不资源文件的情况
     */
    @Bean
    public MessageSource messageSource() {
        LOGGER.debug("--- 进入创建MessageSource实例的代码中");
        //创建一个MessageSource,此处以 ResourceBundleMessageSource 为Bean
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //配置相关的属性值
        messageSource.setDefaultEncoding("UTF-8");
        //设置要读取的资源文件的基础名，可以是多个
        messageSource.setBasenames("validationMessages");
        //是否使用消息代码做为默认的消息
        messageSource.setUseCodeAsDefaultMessage(true);
        //设置缓存时长
        messageSource.setCacheSeconds(60);
        //返回
        LOGGER.debug("-- 创建ResourceBundleMessageSource对象成功，并设置了相关的属性...");
        return messageSource;
    }

    /**********
     * 必须配置的组件： 校验器组件
     */
//    @Bean
    public Validator validator() {
        LOGGER.debug("-- 进入创建校验器的Bean对象代码中....");
        //创建Spring提供的Bean
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        //设置此校验器要读到的资源
        localValidatorFactoryBean.setValidationMessageSource(messageSource());
        //返回
        LOGGER.debug("-- 创建Spring提供的LocalValidatorFactoryBean成功...");
        return localValidatorFactoryBean;
    }
}
