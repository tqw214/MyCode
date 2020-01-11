package com.viger.mycode.Retrofit;

public class ObservableJust<T> extends Observable<T> {

    private T item;

    public ObservableJust(T item) {
        this.item = item;
    }

    @Override
    public void subscribe(Observer<T> observer) {
        observer.onSubscribe();
        try {
            observer.onNext(item);
            observer.onComplete();
        }catch (Exception e) {
            e.printStackTrace();
            observer.onError(e);
        }
    }

}
