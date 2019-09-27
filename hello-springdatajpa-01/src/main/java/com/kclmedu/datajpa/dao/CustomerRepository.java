package com.kclmedu.datajpa.dao;

import com.kclmedu.datajpa.dao.common.BaseRepository;
import com.kclmedu.datajpa.entity.Customer;

import java.util.List;

public interface CustomerRepository extends BaseRepository<Customer, Long> {

    //
    List<Customer> findByAgeGreaterThanEqual(int age);

    //...
}
