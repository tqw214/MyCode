package com.viger.mycode.rxjava;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxBus {

    private Set<Object> subscribers;

    public static volatile RxBus instance;

    private RxBus() {
        subscribers = new CopyOnWriteArraySet<>();
    }

    public static RxBus getInstance() {
        if(instance == null) {
            synchronized (RxBus.class) {
                if(instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public synchronized void register(Object subscriber) {
        subscribers.add(subscriber);
    }

    public synchronized void unRegister(Object subscriber) {
        subscribers.remove(subscriber);
    }

    public void chainProcess(Function function) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(function)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object data) throws Exception {
                        if(data == null) {
                            return;
                        }
                        send(data);
                    }
                });
    }

    private void send(Object data) {
        for(Object subscribe : subscribers) {
            callMethodByAnnotation(subscribe, data);
        }
    }

    private void callMethodByAnnotation(Object subscribe, Object data) {
        Method[] declaredMethods = subscribe.getClass().getDeclaredMethods();
        for(Method method : declaredMethods) {
            RegisterRxBus annotation = method.getAnnotation(RegisterRxBus.class);
            Class<?> parameterType = method.getParameterTypes()[0];
            if(annotation != null && data.getClass().getName().equals(parameterType.getName())) {
                try {
                    method.invoke(subscribe,data);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
