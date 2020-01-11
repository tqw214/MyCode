package com.viger.mvp.view;


import com.viger.mvp.base.BaseView;

import io.reactivex.Observable;

public interface MainContract {

    interface MainView extends BaseView {
        void showTextInfo(String message);
    }

    interface MainPresenter {
        void getTextInfo();
    }

    interface MainModel {
        Observable<String> getTextInfo();
    }

}
