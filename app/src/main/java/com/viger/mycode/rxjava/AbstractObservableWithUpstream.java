package com.viger.mycode.rxjava;

//被观察者
public abstract class AbstractObservableWithUpstream<T, U> extends Observable<U> {
    protected final ObserverbleSource<T> source;

    public AbstractObservableWithUpstream(ObserverbleSource<T> source) {
        this.source = source;
    }
}
