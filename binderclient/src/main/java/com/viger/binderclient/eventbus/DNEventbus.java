package com.viger.binderclient.eventbus;


import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DNEventbus {

    private static DNEventbus instance = new DNEventbus();

    private Map<Object, List<SubscribleMethod>> cacheMap;

    private Handler handler;

    private ExecutorService executorService;

    public static DNEventbus getDefault() {
        return instance;
    }

    private DNEventbus() {
        cacheMap = new HashMap<Object, List<SubscribleMethod>>();
        handler = new Handler(Looper.getMainLooper());
        executorService = Executors.newCachedThreadPool();
    }

    public void register(Object subscriber) {
        List<SubscribleMethod> subscribleMethods = cacheMap.get(subscriber);
        if(subscribleMethods == null) {
            cacheMap.put(subscriber, getSubscribleMethods(subscriber));
        }
    }

    //遍历能够接收事件的方法
    private List<SubscribleMethod> getSubscribleMethods(Object subscriber) {
        List<SubscribleMethod> list = new ArrayList<SubscribleMethod>();
        Class<?> aClass = subscriber.getClass();
        while(aClass != null) {
            String name = aClass.getName();
            if(name.startsWith("java.")||
                    name.startsWith("javax.") ||
                    name.startsWith("android.") ||
                    name.startsWith("androidx.")) {
                break;
            }
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for(Method method : declaredMethods) {
                DNSubscribe annotation = method.getAnnotation(DNSubscribe.class);
                if (annotation == null) {
                    continue;
                }
                //检测这个方法合不合格
                Class<?>[] parameterTypes = method.getParameterTypes();
                if(parameterTypes.length > 1) {
                    throw new RuntimeException("eventbusz只能接收一个参数");
                }
                DNThreadMode dnThreadMode = annotation.threadMode();
                list.add(new SubscribleMethod(method, dnThreadMode, parameterTypes[0]));
            }
            aClass = aClass.getSuperclass();
        }
        return list;
    }

    public void unregister(Object subscriber) {
        List<SubscribleMethod> subscribleMethods = cacheMap.get(subscriber);
        if(subscribleMethods != null) {
            cacheMap.remove(subscriber);
        }
    }

    public void post(final Object object) {
        Iterator<Object> iterator = cacheMap.keySet().iterator();
        while (iterator.hasNext()) {
            //拿到注册类
            final Object next = iterator.next();
            List<SubscribleMethod> subscribleMethods = cacheMap.get(next);
            for(final SubscribleMethod subscribleMethod : subscribleMethods) {
                Class<?> eventType = subscribleMethod.getEventType();
                if(eventType.isAssignableFrom(object.getClass())) {
                    switch (subscribleMethod.getThreadMode()) {
                        case ASYNC:
                            //post方法执行在主线程中
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                executorService.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribleMethod, next, object);
                                    }
                                });
                            }else {
                                invoke(subscribleMethod, next, object);
                            }
                            break;
                        case MAIN:
                            if(Looper.myLooper() == Looper.getMainLooper()){
                                invoke(subscribleMethod, next, object);
                            }else {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribleMethod, next, object);
                                    }
                                });
                            }
                            break;
                        case POSTING:
                            break;
                    }
                }
            }

        }
    }

    private void invoke(SubscribleMethod subscribleMethod, Object next, Object object) {
        try {
            subscribleMethod.getMethod().invoke(next, object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
