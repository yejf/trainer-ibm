package com.kclmedu.mvc.service.impl;

import com.kclmedu.mvc.dao.IEmployeeDao;
import com.kclmedu.mvc.entity.Employee;
import com.kclmedu.mvc.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*******************
 * 业务的实现类
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired  //自动注入
    private IEmployeeDao employeeDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = {RuntimeException.class})
    public void add(Employee e) {
        //1. 插入
        employeeDao.save(e);
        ///
        System.out.println("--- finished...");
    }

    @Override
    public List<Employee> findAll() {
        return employeeDao.findAll();
    }

    @Override
    public Employee findById(Integer id) {
        return employeeDao.findById(id);
    }

    @Override
    public void update(Employee e) {
        employeeDao.update(e);
    }

    @Override
    public void delete(Integer id) {
        employeeDao.deleteById(id);
    }
}
