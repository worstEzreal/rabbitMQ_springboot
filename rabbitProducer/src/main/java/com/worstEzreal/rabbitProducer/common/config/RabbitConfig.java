package com.worstEzreal.rabbitProducer.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Desc
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/15
 */
@Configuration
public class RabbitConfig {
//    @Bean
//    public Queue helloQueue() {
//        return new Queue("hello");
//    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
