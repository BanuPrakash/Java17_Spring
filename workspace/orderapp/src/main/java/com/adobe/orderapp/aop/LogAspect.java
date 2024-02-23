package com.adobe.orderapp.aop;

import com.adobe.orderapp.service.EntityNotFoundException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Aspect
public class LogAspect {
    Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @Before("execution(* com.adobe.orderapp.service.*.*(..))")
    public void logBefore(JoinPoint jp) {
        logger.info("Called : " + jp.getSignature());
        Object[] args = jp.getArgs();
        for (Object arg : args) {
            logger.info("Argument : " + arg);
        }
    }

    @After("execution(* com.adobe.orderapp.service.*.*(..))")
    public void logAfter(JoinPoint jp) {
        logger.info("***********");
    }

    @Around("execution(* com.adobe.orderapp.service.*.*(..))")
    public Object doProfile(ProceedingJoinPoint jp) throws Throwable {
        long startTime = new Date().getTime();
        Object ret = jp.proceed();
        long endTime = new Date().getTime();
        logger.info("Time : " + (endTime - startTime) + " ms");
        return ret;
    }

    @AfterThrowing(pointcut = "execution(* com.adobe.orderapp.service.*.*(..))", throwing = "ex")
    public void logException(EntityNotFoundException ex) throws Throwable {
        logger.info("Exception :" + ex.getMessage());
    }
}