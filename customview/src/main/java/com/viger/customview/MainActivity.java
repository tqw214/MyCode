package com.viger.customview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.activity.CustomView02Activity;
import com.viger.customview.view01.CustomView01Activity;
import com.viger.customview.view03.View03ViewPagerActivity;
import com.viger.customview.view04.View04Activity;
import com.viger.customview.view05.View05Activity;
import com.viger.customview.view06.View06Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_view01)
    Button btnView01;
    @BindView(R.id.btn_view02)
    Button btnView02;
    @BindView(R.id.btn_view03)
    Button btnView03;
    @BindView(R.id.btn_view04)
    Button btnView04;
    @BindView(R.id.btn_view05)
    Button btnView05;

    @BindView(R.id.btn_view06)
    Button btnView06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_view01)
    public void onViewClicked() {
        startActivity(CustomView01Activity.class);
    }

    @OnClick(R.id.btn_view02)
    public void onViewClicked2() {
        startActivity(CustomView02Activity.class);
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }


    @OnClick(R.id.btn_view03)
    public void onViewClicked3() {
        //startActivity(CustomView03Activity.class);
        startActivity(View03ViewPagerActivity.class);
    }
    @OnClick(R.id.btn_view04)
    public void onViewClicked4() {
        //startActivity(CustomView03Activity.class);
        startActivity(View04Activity.class);
    }
    @OnClick(R.id.btn_view05)
    public void onViewClicked5() {
        //startActivity(CustomView03Activity.class);
        startActivity(View05Activity.class);
    }

    @OnClick(R.id.btn_view06)
    public void onViewClicked6() {
        startActivity(View06Activity.class);
    }

}
