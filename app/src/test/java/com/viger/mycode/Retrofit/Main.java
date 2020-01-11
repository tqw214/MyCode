package com.viger.mycode.Retrofit;

import org.junit.Test;

public class Main {

    @Test
    public void run() {
        Observable.just("message")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println("onNext===>" + s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("onError===>");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete===>");
                    }

                    @Override
                    public void onSubscribe() {
                        System.out.println("onSubscribe===>");
                    }
                });
    }

}
