package com.kclmedu.mvc.dao;

import com.kclmedu.mvc.entity.Employee;

import java.io.Serializable;
import java.util.List;

/**************
 * 持久层接口
 */
public interface IEmployeeDao {

    void save(Employee e);

    List<Employee> findAll();

    Employee findById(Serializable id);

}
