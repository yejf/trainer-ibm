package com.kclmedu.mvc.controller;

import com.kclmedu.mvc.entity.Employee;
import com.kclmedu.mvc.service.IEmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller   //也是 @Component 的一种
public class EmployeeController {

    private static final Logger log = Logger.getLogger(EmployeeController.class);
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(value = "/emp/list")
    public String list(@RequestParam(value = "name") String name, Model model) {
        log.debug("--- 请求参数name: "+name);
        //1. 处理用户的请求参数

        //2. 调用业务方法
        List<Employee> employeeList = employeeService.findAll();

        //3. 如果需要，绑定数据到Model中
        model.addAttribute("EMPS_LIST_KEY",employeeList);

        //4. 跳转到目标资源【JSP, requestMapping, ...】
        return "list"; //此处返回的是逻辑视图名
    }

    @RequestMapping(value = "/emp/{id}",produces = "application/json")
    @ResponseBody  //表示以方法的返回值做为响应体。
    public Employee getEmployee(@PathVariable("id") Integer id) {
        //Employee
        Employee e = employeeService.findById(id);
        //
        return e;
    }

    @RequestMapping(value = "/emp/toadd")
    public String toAdd() {
        return "add";
    }

    @RequestMapping(value = "/emp/add")
    public String addEmp(Employee e) {
        log.debug(e);
        //
        employeeService.add(e);
        //跳转
        return "forward:/emp/list"; //如果返回的字符串中，使用了 forward或redirect前缀，
                                    // 则不经过视图解析器
    }
}
