package com.hc.service;

import com.hc.config.AppConfig;
import com.hc.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeServiceTest{

    @Autowired   //自动导入
    private IEmployeeService employeeService;

    @Test
    public void testAdd() {
        //模拟一个员工
        Employee e =
                new Employee("jack",new Date(),
                        10200,"JAVA开发工程师");

        employeeService.add(e);

        //
        List<Employee> emps = employeeService.findAll();
        //
        for(Employee ee :emps) {
            System.out.println(ee);
        }
    }
}
