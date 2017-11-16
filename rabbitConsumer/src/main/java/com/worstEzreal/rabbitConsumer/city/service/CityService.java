package com.worstEzreal.rabbitConsumer.city.service;

import com.worstEzreal.rabbitConsumer.city.dao.CityDao;
import com.worstEzreal.rabbitConsumer.city.entity.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 城市Service
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
@Service
@RabbitListener(queues = "hello")
public class CityService {

    @Autowired
    private CityDao cityDao;

    private static final Logger logger = LoggerFactory.getLogger(CityService.class);

    public City getCityById(String id) {
        return cityDao.getCityById(id);
    }

    @RabbitHandler
    public void receive(String msg) {
        logger.info("【接收消息】msg={}", msg);
    }

}
