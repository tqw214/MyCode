package com.viger.binderclient.eventbus;

import java.lang.reflect.Method;

public class SubscribleMethod {

    private Method method;

    private DNThreadMode threadMode;

    private Class<?> eventType;


    public SubscribleMethod(Method method, DNThreadMode threadMode, Class<?> eventType) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public DNThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(DNThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public void setEventType(Class<?> eventType) {
        this.eventType = eventType;
    }
}
