package com.kclmedu.mvc.controller;

import com.kclmedu.mvc.BaseControllerTests;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerTest extends BaseControllerTests {

    @Test
    public void testgetUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/v1/api/users"))
                .andExpect(status().is(200))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
