package com.viger.mvp;

import android.widget.TextView;

import com.viger.mvp.base.BaseMvpActivity;
import com.viger.mvp.presenter.MainPresenter;
import com.viger.mvp.view.MainContract;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.MainView {

    private TextView textView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        getPresenter().getTextInfo();
    }

    @Override
    protected void initView() {
        textView = findViewById(R.id.textView);

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showTextInfo(String message) {
        textView.setText(message);
    }
}
