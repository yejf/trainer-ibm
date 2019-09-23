package com.kclmedu.springboot.service;

import com.kclmedu.springboot.entity.Customer;
import com.kclmedu.springboot.entity.CustomerLevel;
import com.kclmedu.springboot.entity.Gender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerServiceTests {

    @Autowired
    private ICustomerService customerService;

    @Test
    public void testSave() {
        Customer c = new Customer();
        c.setName("张无记[主]");
        c.setPhone("19800997765");
        c.setAge(23);
        c.setGender(Gender.男);
        c.setLevel(CustomerLevel.HIGH);
        //
        customerService.save(c);
    }

    @Test
    public void testFindAll() {
        List<Customer> customers = customerService.findAll();
        //
        if(customers != null) {
            customers.forEach(System.out::println);
        }
    }
}
