package com.viger.customview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.activity.CustomView01Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_view01)
    Button btnView01;

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

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this ,clazz);
        startActivity(intent);
    }

}
