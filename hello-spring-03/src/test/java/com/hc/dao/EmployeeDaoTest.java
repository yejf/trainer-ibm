package com.hc.dao;

import com.hc.config.AppConfig;
import com.hc.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/*****
 * 使用 spring-test 组件进行单元测试
 */
@ContextConfiguration(classes = AppConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeeDaoTest {

    @Autowired
    private IEmployeeDao employeeDao;

    @Test
    public void testSave() {
        Employee e = new Employee();
        e.setName("员工二");
        e.setSalary(12098);
        e.setTitle("Java开发工程师");
        e.setStart_date(new Date());
        //
        employeeDao.save(e);
    }

    @Test
    public void testFindAll() {
        List<Employee> employees = employeeDao.findAll();
        //
        if(employees != null) {
            employees.forEach(System.out::println);
        }
    }

    @Test
    public void testFindById() {
        Employee employee = employeeDao.findById(1);
        //
        System.out.println(employee);
    }
}
