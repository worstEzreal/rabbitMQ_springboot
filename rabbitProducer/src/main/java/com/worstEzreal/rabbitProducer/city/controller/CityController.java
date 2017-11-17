package com.worstEzreal.rabbitProducer.city.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.worstEzreal.rabbitProducer.city.entity.City;
import com.worstEzreal.rabbitProducer.city.service.CityService;
import com.worstEzreal.rabbitProducer.common.BaseController;
import com.worstEzreal.rabbitProducer.common.Result;
import com.worstEzreal.rabbitProducer.common.utils.LocalHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * 城市Controller
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
@RestController
@RequestMapping("city")
public class CityController extends BaseController {

    @Value("${city.api-context}")
    private String cityApiContext;

    @Autowired
    private CityService cityService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/name/{id}")
    public Result name(@PathVariable String id) throws IOException {
        String url = cityApiContext + id;
        String content = LocalHttpClient.post(url, null, JSONObject.class).getString("content");
        City city = JSON.parseObject(content, City.class);
        return new Result("0", "", city.getName());
    }

    @RequestMapping("/name2/{id}")
    public Result name2(@PathVariable String id) {
        String url = cityApiContext + id;
        City city = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<Result<City>>() {
        }).getBody().getContent();
        return new Result("0", "", city.getName());
    }


    @RequestMapping("hello")
    public Result hello() {
        cityService.sendMsg();
        return new Result("", "发送成功", null);
    }


}
