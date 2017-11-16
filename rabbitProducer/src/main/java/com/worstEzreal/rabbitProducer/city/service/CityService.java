package com.worstEzreal.rabbitProducer.city.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 城市Service
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
@Service
public class CityService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private final static Logger logger = LoggerFactory.getLogger(CityService.class);

    public void sendMsg(){
        rabbitTemplate.convertAndSend("hello","hello" + new Date());
        logger.info("【发送消息】msg={}", "hello" + new Date());
    }
}
