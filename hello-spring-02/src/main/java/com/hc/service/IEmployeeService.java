package com.hc.service;

import com.hc.entity.Employee;

import java.util.List;

/************
 * 业务层接口
 */
public interface IEmployeeService {

    void add(Employee e);

    List<Employee> findAll();

}
