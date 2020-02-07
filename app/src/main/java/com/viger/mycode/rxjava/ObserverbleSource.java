package com.viger.mycode.rxjava;

public interface ObserverbleSource<T> {

    void subscribe(Observer<? super T> observer);

}
