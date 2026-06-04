package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Bước 1: @Before - Áp dụng cho mọi method trong Controller
    @Before("execution(* com.example.demo.controller.BookController.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info(">>> [AOP @Before] Calling Controller Method: {} | Input Params: {}", methodName, args);
    }

    // Bước 2: @AfterReturning - Áp dụng cho mọi method trong Service, lấy được kết quả trả về
    @AfterReturning(pointcut = "execution(* com.example.demo.service.BookService.*(..))", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("<<< [AOP @AfterReturning] Service Method Executed: {} | Returned Value: {}", methodName, result);
    }

    // Bước 3: @Around - Áp dụng cho mọi method trong Controller để đo hiệu năng thời gian
    @Around("execution(* com.example.demo.controller.BookController.*(..))")
    public Object logAroundController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = proceedingJoinPoint.getSignature().getName();

        try {
            // Cho phép method thực thi tiếp tục nghiệp vụ
            return proceedingJoinPoint.proceed();
        } finally {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("⏱️ [AOP @Around] Controller Method: {} executed in {} ms", methodName, executionTime);
        }
    }
}