package com.mirea.optics_shop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@Component
@org.aspectj.lang.annotation.Aspect
public class LoggingAspect {
    @Before("allServiceMethods()")
    public void logTimeStart(JoinPoint joinPoint) {
        LocalTime localTime = LocalTime.now();
        log.info("Method {} start at {}", joinPoint.getSignature(), localTime);
    }
    @After("allServiceMethods()")
    public void logTimeEnd(JoinPoint joinPoint) {
        LocalTime localTime = LocalTime.now();
        log.info("Method {} end at {}", joinPoint.getSignature(), localTime);
    }
    @Pointcut("within(com.mirea.optics_shop.service.*)")
    public void allServiceMethods() {}
}
