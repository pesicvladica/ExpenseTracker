package com.pesicvladica.expensetracker.util.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    @Around("@annotation(com.pesicvladica.expensetracker.util.logging.TrackTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        System.out.println("[TIME TRACKING]: Method " + joinPoint.getSignature() + " executed in " + duration + " ms");

        return result;
    }
}
