package com.worstEzreal.rabbitConsumer.city.controller;

import com.worstEzreal.rabbitConsumer.BaseControllerTest;
import com.worstEzreal.rabbitConsumer.city.entity.City;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * 城市Controller测试
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/16
 */
public class CityControllerTest extends BaseControllerTest{
    @Test
    public void city() throws Exception {
        String cityId = "2";
        doPost("/city/" + cityId, null)
                .andExpect(jsonPath("$.status").value("0"))
                .andDo(print());
    }

}