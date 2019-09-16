package com.kclmedu.mvc.dao.impl;

import com.kclmedu.mvc.dao.IEmployeeDao;
import com.kclmedu.mvc.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/******
 * 持久层的实现类，采用 Spring提供的JdbcTemplate 模板类来实现
 */
@Repository    //功能同 @Component,只是语义上的加强
public class EmployeeDaoImpl implements IEmployeeDao {

    @Autowired  //自动注入
    private JdbcTemplate template;

    @Override
    public void save(Employee e) {
        //
        if(e != null) {
            //
            String sql = "insert into tbl_emp(name,start_date,salary,title) values(?,?,?,?)";
            //
            template.update(sql,e.getName(),e.getStart_date(),e.getSalary(),e.getTitle());
        }
    }

    @Override
    public List<Employee> findAll() {
        //
        String sql = "select id,name,start_date,salary,title from tbl_emp";
        //准备一个把结果集装配到实体类对象的 对象
        BeanPropertyRowMapper<Employee> bprm = new BeanPropertyRowMapper<>(Employee.class);
        //
        return template.query(sql, bprm);
    }

    @Override
    public Employee findById(Serializable id) {
        if(id != null) {
            //
            String sql = "select id,name,start_date,salary,title from tbl_emp where id = ?";
            //准备一个把结果集装配到实体类对象的 对象
            BeanPropertyRowMapper<Employee> bprm = new BeanPropertyRowMapper<>(Employee.class);
            //
            return template.queryForObject(sql, bprm, id);
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        //
        if(id != null) {
          String sql = "delete from tbl_emp where id = ?";
            //          //
            template.update(sql, id);
        }
    }

    @Override
    public void update(Employee e) {
        if(e != null) {
            //
            String sql = "update tbl_emp set name=?,start_date=?,salary=?,title=? where id = ?";
            //
            template.update(sql,e.getName(),e.getStart_date(),e.getSalary(),e.getTitle(),e.getId());
        }
    }
}
