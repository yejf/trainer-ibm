package com.kclmedu.springboot.entity;

import lombok.Data;

@Data  //IDEA要想在编译期间“看到”生成的方法，需要安装 lombok插件
public class Customer {

    private Long id;

    private String name;

    private String phone;

    private CustomerLevel level;   //mapping column name:  c_level

    private int age;

    private Gender gender;

}
