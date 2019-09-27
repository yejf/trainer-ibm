package com.kclmedu.datajpa.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TBL_CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;
    @Column
    private int age;

    @Column
    private String phone;

    public Customer() {
    }

    public Customer(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }
}
