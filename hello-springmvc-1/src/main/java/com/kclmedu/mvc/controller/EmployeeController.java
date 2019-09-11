package com.kclmedu.mvc.controller;

import com.kclmedu.mvc.entity.Employee;
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

    private static List<Employee> emps = new ArrayList<>();
    static {
        emps.addAll(Arrays.asList(
                new Employee(1,"张三",new Date(),
                        8888,"Java开发工程师"),
                new Employee(2,"李四",new Date(),
                        9999,"Go开发工程师"),
                new Employee(3,"王五",new Date(),
                        10000,"C开发工程师"),
                new Employee(4,"小二",new Date(),
                        11000,"C++开发工程师")
        ));
    }

    @RequestMapping(value = "/emp/list")
    public String list(@RequestParam(value = "name") String name, Model model) {
        System.out.println("--- 请求参数name: "+name);
        //1. 处理用户的请求参数

        //2. 调用业务方法
        List<String> names = Arrays.asList("jack","ann","solo","peter",name);

        //3. 如果需要，绑定数据到Model中
        model.addAttribute("NAMES_KEY", names);
        //
        model.addAttribute("EMPS_LIST_KEY",emps);

        //4. 跳转到目标资源【JSP, requestMapping, ...】
        return "list"; //此处返回的是逻辑视图名
    }

    @RequestMapping(value = "/emp/{id}",produces = "application/json")
    @ResponseBody  //表示以方法的返回值做为响应体。
    public Employee getEmployee(@PathVariable("id") Integer id) {
        //模拟一个Employee 集合对象
        List<Employee> emps = Arrays.asList(
                new Employee(1,"张三",new Date(),
                        8888,"Java开发工程师"),
                new Employee(2,"李四",new Date(),
                9999,"Go开发工程师"),
                new Employee(3,"王五",new Date(),
                10000,"C开发工程师"),
                new Employee(4,"小二",new Date(),
                        11000,"C++开发工程师")
                );

        Employee e = emps.get(id);
        //
        return e;
    }

    @RequestMapping(value = "/emp/toadd")
    public String toAdd() {
        return "add";
    }

    @RequestMapping(value = "/emp/add")
    public String addEmp(Employee e) {
        System.out.println(e);
        //
        emps.add(e);
        //跳转
        return "forward:/emp/list"; //如果返回的字符串中，使用了 forward或redirect前缀，
                                    // 则不经过视图解析器
    }
}
