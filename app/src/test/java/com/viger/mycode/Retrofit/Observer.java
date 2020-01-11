package com.viger.mycode.Retrofit;

public interface Observer<T> {

    void onNext(T t);
    void onError(Throwable e);
    void onComplete();
    void onSubscribe();

}
