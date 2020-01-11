package com.viger.mvp.model;


import com.viger.mvp.view.MainContract;

import io.reactivex.Observable;

public class MainModel implements MainContract.MainModel {

    @Override
    public Observable<String> getTextInfo() {
        return Observable.just("message");
    }
}
