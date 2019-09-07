package com.hc.config;

import com.hc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.*;

/**************************
 * Spring 注解配置类
 */
@Configuration   //表明这是一个Spring 的配置类
@ComponentScan(value = "com.hc.service.impl")
//导入属性文件
@PropertySource(value = "classpath:message.properties")
//导入 xml 配置
@ImportResource(value = "classpath:applicationContext.xml")
public class AppConfig {

    /*@Value(value = "${emp.salary}")
    private double salary;*/
    @Autowired   //自动注入
    private Environment env;  //这个对象除了加载我们自己读取的属性文件外，
    //它还会额外加载JVM的系统属性

    @Bean   //告诉spring IOC容器，把此方法的返回值当做一个bean
            //由容器管理起来
    public List<Employee> initList() {
        System.out.println("---- "+
                Double.parseDouble(env.getProperty("emp.salary")));
        //通过evn来获取JVM的系统属性
        System.out.println("--- "+env.getProperty("TEMP"));
        System.out.println("--- invoke initList method...");
        return new ArrayList<>();
    }

    @Bean
    public List<Employee> initList2() {
        System.out.println("---invoke initList2() ... ");
        return new LinkedList<>();
    }
}
