package com.worstEzreal.rabbitConsumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.CharacterEncodingFilter;


@SpringBootApplication
@ComponentScan(basePackages = {"com.worstEzreal"})
@MapperScan("com.worstEzreal.rabbitConsumer.**.dao")
public class RabbitConsumerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(RabbitConsumerApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		try {
			CharacterEncodingFilter filter = new CharacterEncodingFilter();
			filter.setEncoding("UTF-8");
			filterRegistrationBean.setFilter(filter);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return filterRegistrationBean;
	}
}
