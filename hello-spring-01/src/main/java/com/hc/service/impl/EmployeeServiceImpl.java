package com.hc.service.impl;

import com.hc.entity.Employee;
import com.hc.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*******************
 * 业务的实现类
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

//    private List<Employee> empList = new ArrayList<>();
    @Autowired  //自动注入 [可能会存在二义性], search by type
    @Qualifier(value = "list")  //进一步限定,  search by name
//    @Resource(name="initList")   //这个是JAVAEE 规范中官方的注解
    private List<Employee> empList;

    @Override
    public void add(Employee e) {
        System.out.println("--- 添加Employee实例到集合中");
        //
        if(null != empList) {
            empList.add(e);
        } else {
            throw new RuntimeException("容器没有初始化...");
        }
    }

    @Override
    public List<Employee> findAll() {
        System.out.println("--- 返回所有的员工实例");
        //
        return this.empList;
    }
}
