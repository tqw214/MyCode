package com.viger.mycode.Retrofit;

public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> ObservableSource<T> just(T item) {

        return onAssembly(new ObservableJust<T>(item));
    }

    private static <T> ObservableSource<T> onAssembly(ObservableJust<T> observableJust) {
        return observableJust;
    }

    @Override
    public void subscribe(Observer<T> observer) {

    }
}
