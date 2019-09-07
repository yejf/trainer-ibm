package com.hc.config;

import com.hc.entity.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;
import java.util.List;

/***********************
 * Spring 注解配置类
 */
@Configuration
@ComponentScan(value = {"com.hc.service.impl","com.hc.aspect"})
@EnableAspectJAutoProxy  //表示启动AOP 机制
public class AppConfig {

    @Bean
    public List<Employee> getList() {
        //
        return new ArrayList<>();
    }
}
