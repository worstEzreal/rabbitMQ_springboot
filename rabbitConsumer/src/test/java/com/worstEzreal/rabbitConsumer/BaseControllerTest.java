package com.worstEzreal.rabbitConsumer;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 基础 Controller 单元测试
 *
 * @author qinrui@yonyou.com
 * @version 1.0.0
 * @date 2017/4/6
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BaseControllerTest {

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    protected ResultActions doPost(String url, Object params) throws Exception {
        return this.mockMvc.perform(
                post(url).contentType(MediaType.APPLICATION_JSON)
//                        .header("token", token)
                        .content(JSON.toJSONString(params)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    protected ResultActions doGet(String url, Object params) throws Exception {
        return this.mockMvc.perform(
                get(url).contentType(MediaType.APPLICATION_JSON)
//                        .header("token", token)
                        .content(JSON.toJSONString((params))).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
