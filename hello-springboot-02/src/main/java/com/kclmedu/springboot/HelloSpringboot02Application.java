package com.kclmedu.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class HelloSpringboot02Application {

    public static void main(String[] args) {
        SpringApplication.run(HelloSpringboot02Application.class, args);
    }

}
