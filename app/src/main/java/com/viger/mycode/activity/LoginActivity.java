package com.viger.mycode.activity;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.mycode.R;
import com.viger.mycode.utils.CustomToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    public void click(View view) {
        CustomToast.showToast(LoginActivity.this, "主线程toast");
    }

    @OnClick(R.id.btn_login2)
    public void click2(View view) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                CustomToast.showToast(LoginActivity.this, "子线程toast");
                Looper.loop();
            }
        }.start();
    }

}
