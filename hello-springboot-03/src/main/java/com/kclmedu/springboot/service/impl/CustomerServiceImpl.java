package com.kclmedu.springboot.service.impl;

import com.kclmedu.springboot.dao.ICustomerDao;
import com.kclmedu.springboot.entity.Customer;
import com.kclmedu.springboot.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(transactionManager="masterTxManager")
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    @Override
    public void save(Customer c) {
        customerDao.save(c);
    }

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }
}
