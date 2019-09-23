package com.kclmedu.springboot.service;

import com.kclmedu.springboot.entity.Customer;

import java.util.List;

public interface ICustomerService {

    void save(Customer c);

    Customer findById(Long id);

    List<Customer> findAll();

}
