//package com.btb.config;
//
//import com.btb.annoation.Log;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//
//@Aspect
//@Component
//public class SysLogAspect {
//
//    /**
//     * logger
//     */
//    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogAspect.class);
//
//
//    @Pointcut("@annotation(com.aldeo.common.annotation.Log)")
//    public void logPointCut() {
//    }
//
//    @Around("logPointCut()")
//    public Object around(ProceedingJoinPoint point) throws Throwable {
//        long beginTime = System.currentTimeMillis();
//        // 目标方法
//        Object result = point.proceed();
//        long time = System.currentTimeMillis() - beginTime;
//
//        Log syslog = Method.getAnnotation(Log.class);
//        if (syslog != null) {
//            // 注解上的描述
//            LOGGER.info(syslog.value());
//        }
//
//        // 保存日志
//        try {
//            saveLog(point, time);
//        } catch (Exception e) {
//            LOGGER.error("==================================> saveSysLog.around.exception: " + e.getMessage(), e);
//        }
//        return result;
//    }
