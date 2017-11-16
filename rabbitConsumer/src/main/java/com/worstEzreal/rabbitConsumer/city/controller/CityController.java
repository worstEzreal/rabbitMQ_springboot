package com.worstEzreal.rabbitConsumer.city.controller;

import com.worstEzreal.rabbitConsumer.city.service.CityService;
import com.worstEzreal.rabbitConsumer.common.BaseController;
import com.worstEzreal.rabbitConsumer.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 城市Controller
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
@RestController
@RequestMapping("city")
public class CityController extends BaseController{

    @Autowired
    private CityService cityService;

    @RequestMapping("/{id}")
    public Result city(@PathVariable String id){
        return new Result("0","",cityService.getCityById(id));
    }

}
