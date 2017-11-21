package com.worstEzreal.rabbitProducer.common.http;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * http 调用切面
 *
 * @author zengxzh@yonyou.com
 * @version V1.0.0
 * @date 2017/11/20
 */
@Aspect
@Component
public class HttpCallAdvice {

    private Logger logger = LoggerFactory.getLogger(HttpCallAdvice.class);

    @Around("execution(public * org.springframework.web.client.RestTemplate.*(..))")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        logger.info("method={} | type=request | params={}", methodName, JSON.toJSONString(args));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logger.error("method={}", methodName, e);
            throw e;
        }

        logger.info("method={} | type=response | result={}", methodName, JSON.toJSONString(result));
        return result;
    }

}
