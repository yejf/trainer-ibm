package com.kclmedu.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages="com.kclmedu.springboot.dao"
        ,sqlSessionFactoryRef = "masterSesssionFactory")
public class HelloSpringboot03Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringboot03Application.class, args);
        System.out.println("--- SpringBootApplication run success....");
    }

}
