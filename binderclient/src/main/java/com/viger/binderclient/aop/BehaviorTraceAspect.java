package com.viger.binderclient.aop;

import android.os.SystemClock;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Random;

@Aspect
public class BehaviorTraceAspect {

//    @Pointcut("execution(@com.viger.binderclient.aop.BehaviorTrace * * (..))")
//    public void methodAnnottatedWithBehaviorTrace() {
//        Log.d("tag","methodAnnottatedWithBehaviorTrace=>");
//    }


//    @Before("execution(@com.viger.binderclient.aop.BehaviorTrace * * (..))")
//    public void beforeMethod() throws Throwable{
//        Log.d("tag","before method is executed");
//    }

    @After("execution(@com.viger.binderclient.aop.UserInfoBehaviorTrace * * (..))")
    public void afterMethod() throws Throwable{
        Log.d("tag","after UserInfoBehaviorTrace method is executed");
    }


    @Around("execution(@com.viger.binderclient.aop.BehaviorTrace * * (..))")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        String value = methodSignature.getMethod().getAnnotation(BehaviorTrace.class).value();

        long begin = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        SystemClock.sleep(new Random().nextInt(2000));
        long duration = System.currentTimeMillis() - begin;
        Log.d("tag", String.format("%s功能：%s类的%s方法执行了，用时%d ms",
                value, className, methodName, duration));
        return result;
    }



}
