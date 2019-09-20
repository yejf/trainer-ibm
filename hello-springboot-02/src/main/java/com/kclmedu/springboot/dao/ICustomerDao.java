package com.kclmedu.springboot.dao;

import com.kclmedu.springboot.entity.Customer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ICustomerDao {

    @Insert("insert into tbl_customer(name,phone,c_level,age,gender) " +
            "values (#{name},#{phone} ,#{level} ,#{age} ,#{gender})")
    void save(Customer customer);

    @Select("select id,name,phone,c_level \"level\",age,gender from tbl_customer where id = #{id}")
    Customer findById(Long id);

    @Select("select id,name,phone,c_level \"level\",age,gender from tbl_customer")
    List<Customer> findAll();

}
