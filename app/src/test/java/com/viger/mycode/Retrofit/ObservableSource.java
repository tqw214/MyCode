package com.viger.mycode.Retrofit;

public interface ObservableSource<T> {
    void subscribe(Observer<T> observer);
}
