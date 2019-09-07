package com.hc.service.impl;

import com.hc.annotations.Special;
import com.hc.entity.Employee;
import com.hc.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*******************
 * 业务的实现类
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired  //自动注入 [可能会存在二义性], search by type
    private List<Employee> empList;

    @Override
    public void add(Employee e) {
        //
        if(null != empList) {
            //模拟一个随机异常
            randomException();
            empList.add(e);
        } else {
            throw new RuntimeException("容器没有初始化...");
        }
        System.out.println("--- print with add method....");
    }

    private void randomException() {
        int i = (int)(Math.random() * 100);
        if((i & 1) == 0) {
            throw new IllegalStateException("添加对象失败...");
        }
    }

    @Override
    @Special
    public List<Employee> findAll() {
        //
        return this.empList;
    }
}
