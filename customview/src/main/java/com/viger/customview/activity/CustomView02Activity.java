package com.viger.customview.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.R;
import com.viger.customview.view.QQStepView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomView02Activity extends AppCompatActivity {


    @BindView(R.id.qqStepView)
    QQStepView qqStepView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_02);
        ButterKnife.bind(this);

        qqStepView.setStepMax(1000);
        qqStepView.setCurrentStep(300);
        qqStepView.startAnimation();

    }
}
