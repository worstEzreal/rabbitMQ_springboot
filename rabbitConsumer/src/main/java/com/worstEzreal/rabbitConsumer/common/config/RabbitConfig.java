package com.worstEzreal.rabbitConsumer.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Desc
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/15
 */
@Configuration
public class RabbitConfig {
    @Bean
    public Queue hello(){
        return new Queue("hello");
    }
}
