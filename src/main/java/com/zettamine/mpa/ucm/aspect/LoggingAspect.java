package com.zettamine.mpa.ucm.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * Aspect class for logging method calls in the com.zettamine.mi.service package.
 */
@Configuration
@Aspect
@Slf4j
public class LoggingAspect {

    // ANSI escape codes for color
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";

    /**
     * Logs information before the execution of methods in the target package.
     * @param joinPoint The JoinPoint representing the method being intercepted.
     */
    @Before("execution(* com.zettamine.mpa.ucm.*.*.*(..))")
    public void logBeforeMethodCall(JoinPoint joinPoint) {
        log.info(ANSI_GREEN + joinPoint.getSignature().getDeclaringTypeName() + " - Starting Execution of - \"" + joinPoint.getSignature().getName() + "()\" - with " + joinPoint.getArgs().length + " parameters" + ANSI_RESET);
    }

    /**
     * Logs information after the execution of methods in the target package.
     * @param joinPoint The JoinPoint representing the method being intercepted.
     */
    @After("execution(* com.zettamine.mpa.ucm.*.*.*(..))")
    public void logAfterMethodCall(JoinPoint joinPoint) {
        log.info(ANSI_GREEN + joinPoint.getSignature().getDeclaringTypeName() + " - Method - \"" + joinPoint.getSignature().getName() + "()\" has executed" + ANSI_RESET);
    }

    /**
     * Logs information after the successful execution of methods in the target package.
     * @param joinPoint The JoinPoint representing the method being intercepted.
     */
    @AfterReturning(pointcut = "execution(* com.zettamine.mpa.ucm.*.*.*(..))", returning = "returnValue")
    public void logAfterSuccessfulMethodCall(JoinPoint joinPoint) {
        log.info(ANSI_GREEN + joinPoint.getSignature().getDeclaringTypeName() + " - Execution Completed for \"" + joinPoint.getSignature().getName() + "()\" with " + joinPoint.getArgs().length + " arguments and returns - " + ANSI_RESET);
    }

    /**
     * Logs information after an exception is thrown from methods in the target package.
     * @param joinPoint The JoinPoint representing the method being intercepted.
     * @param ex The exception thrown by the intercepted method.
     */
    @AfterThrowing(pointcut = "execution(* com.zettamine.mpa.ucm.*.*.*(..))", throwing = "ex")
    public void logMethodCallAfterException(JoinPoint joinPoint, Exception ex) {
        log.info(ANSI_RED + joinPoint.getSignature().getDeclaringTypeName() + " - " + joinPoint.getSignature().getName() + "() fired an exception -> " + ex.toString() + ANSI_RESET);
    }
}
