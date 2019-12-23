package com.viger.mycode.utils;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class SectionAspect {


    /**
     * 找到处理的切点
     * * *(..)  可以处理所有的方法
     */
    @Pointcut("execution(@com.viger.mycode.utils.CheckNet * *(..))")
    public void checkNetBehavior() {

    }

    /**
     * 处理切面
     */
    @Around("checkNetBehavior()")
    public Object checkNet(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e("TAG", "checkNet");
        // 做埋点  日志上传  权限检测（我写的，RxPermission , easyPermission） 网络检测
        // 网络检测
        // 1.获取 CheckNet 注解  NDK  图片压缩  C++ 调用Java 方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        CheckNet checkNet = signature.getMethod().getAnnotation(CheckNet.class);
        if (checkNet != null) {
            // 2.判断有没有网络  怎么样获取 context?
            Object object = joinPoint.getThis();// View Activity Fragment ； getThis() 当前切点方法所在的类

                if (!isNetworkAvailable()) {
                    // 3.没有网络不要往下执行
                    System.out.println("没有网络，不执行了。。。。");
                    return null;
                }

        }
        return joinPoint.proceed();
    }


    /**
     * 检查当前网络是否可用
     *
     * @return
     */
    private static boolean isNetworkAvailable() {
        return true;
    }

}
