package com.shatbha_shop.shatbha_shop.AOP;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint; // Import ProceedingJoinPoint for proceed() method
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class LoggerAspect  {

    Logger log = LoggerFactory.getLogger(LoggerAspect.class);

    @Around(value = "execution(* com.shatbha_shop.shatbha_shop.Services..*(..))")
    public Object Logger(ProceedingJoinPoint  joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("KPI:");
        sb.append("[").append(joinPoint.getKind()).append("]\tfor: ").append(joinPoint.getSignature())
                .append("\twithArgs: ").append("(").append(String.join( ",",Arrays.toString(joinPoint.getArgs()))).append(")");
        sb.append("\ttook: ");
        Object returnValue = joinPoint.proceed();

        log.info(sb.append(System.currentTimeMillis() - startTime).append((" ms. ").toString()).toString());

        return returnValue;

    }
    
}
