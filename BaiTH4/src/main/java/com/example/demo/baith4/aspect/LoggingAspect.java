package com.example.demo.baith4.aspect;

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

    // Định nghĩa Pointcut trỏ vào tất cả các method trong StudentController
    @Pointcut("execution(* com.example.demo.baith4.controller.StudentController.*(..))")
    public void controllerMethods() {}

    // 1. @Before: Log thông tin request (Tên method + Tham số truyền vào) với cấp độ INFO
    @Before("controllerMethods()")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info(" [INFO LOG] AOP Before: Đang gọi Method [{}] với tham số truyền vào: {}", methodName, args);
    }

    // 2. @Around: Đo đếm chính xác thời gian thực thi của Controller
    @Around("controllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed(); // Cho phép method thực thi tiếp tục

        long executionTime = System.currentTimeMillis() - start;
        logger.info("⏱ [INFO LOG] AOP Around: Method [{}] chạy mất {} ms", joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }

    // 3. @AfterThrowing: Log thông tin lỗi ngay khi tầng Service phát sinh lỗi
    @AfterThrowing(pointcut = "execution(* com.example.demo.baith4.service.StudentService.*(..))", throwing = "exception")
    public void logAfterServiceThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.warn(" AOP AfterThrowing: Phát hiện ngoại lệ ném ra tại Method [{}] - Lý do: {}",
                joinPoint.getSignature().getName(), exception.getMessage());
    }
}