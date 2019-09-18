package com.kclmedu.mvc;

import com.kclmedu.mvc.config.WebMvcConfig;
import com.kclmedu.mvc.controller.EmployeeController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@ContextConfiguration(classes = WebMvcConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration  //是为了让spring测试框架注入 WebApplicationContext对象
public class BaseControllerTests {

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void initMockMvc() {
        //初始化MockMvc
        this.mockMvc = webAppContextSetup(wac).build();
        //如果针对单个控制器的测试
        /*EmployeeController ec = new EmployeeController();
        this.mockMvc = standaloneSetup(ec).build();*/
    }
}
