package com.viger.mvp.presenter;

import com.viger.mvp.base.BasePresenter;
import com.viger.mvp.model.MainModel;
import com.viger.mvp.view.MainContract;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainPresenter extends BasePresenter<MainContract.MainView> implements MainContract.MainPresenter {

    private MainContract.MainModel mainModel;
    //private MainContract.MainView mainView;

    public MainPresenter() {
        mainModel = new MainModel();
    }

    @Override
    public void getTextInfo() {
        mainModel.getTextInfo().subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                //mainView.showTextInfo(s);
                getView().showTextInfo(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

}
