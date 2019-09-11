package com.kclmedu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller   //也是 @Component 的一种
public class EmployeeController {

    @RequestMapping(value = "/emp/list")
    public String list(@RequestParam(value = "name") String name, Model model) {
        System.out.println("--- 请求参数name: "+name);
        //1. 处理用户的请求参数

        //2. 调用业务方法
        List<String> names = Arrays.asList("jack","ann","solo","peter",name);

        //3. 如果需要，绑定数据到Model中
        model.addAttribute("NAMES_KEY", names);
        //4. 跳转到目标资源【JSP, requestMapping, ...】
        return "/WEB-INF/jsp/list.jsp";
    }
}
