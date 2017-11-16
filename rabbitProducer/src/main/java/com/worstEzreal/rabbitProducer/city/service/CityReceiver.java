package com.worstEzreal.rabbitProducer.city.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


/**
 * 城市Service
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/14
 */
@Service
//@RabbitListener(queues = "hello")
public class CityReceiver {

    private static final Logger logger = LoggerFactory.getLogger(CityReceiver.class);

//    @RabbitHandler
//    public void receive(String msg) {
//        logger.info("【接收消息】msg={}", msg);
//    }

}
