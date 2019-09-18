package com.kclmedu.mvc.controller;

import com.kclmedu.mvc.BaseControllerTests;
import com.kclmedu.mvc.config.WebMvcConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmployeeControllerTest extends BaseControllerTests {

    @Test
    public void testList() throws Exception {
        //通过mockMvc执行请求
        mockMvc.perform(get("/emp/list").param("name","jack"))
                .andExpect(status().is(200))  //做断言
                .andExpect(view().name("list")) //断言
                .andDo(print());  //打印结果
    }

    @Test
    public void testgetEmployee() throws Exception {
        //
        MvcResult mvcResult = mockMvc.perform(post("/emp/2"))
                                    .andExpect(status().is(200))
                                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                                    .andExpect(content().json("{\"id\":2,\"name\":\"员工二\",\"start_date\":1567699200000,\"salary\":12098.0,\"title\":\"Java开发工程师\"}"))
                                    .andReturn();
        //通过 mvcResult来获取数据
        System.out.printf("本次请求响应的数据模型：%s",mvcResult.getResponse().getContentAsString());

    }
}
