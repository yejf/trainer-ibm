package com.kclmedu.springboot.controller;

import com.kclmedu.springboot.dao.ICustomerDao;
import com.kclmedu.springboot.entity.Customer;
import com.kclmedu.springboot.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "/customers",produces = "application/json")
    @ResponseBody
    public List<Customer> getCustomers() {
        //
        return customerService.findAll();
    }

    @RequestMapping(value = "/customer/list")
    public String listCustomer(Model model) {
        //
        List<Customer> customerList = customerService.findAll();
        //绑定到页面
        model.addAttribute("CUSTOMER_LIST", customerList);
        //返回逻辑视图
        return "customer/list";  //真正的位置是： /templates/thymeleaf/customer/list.html
    }
}
