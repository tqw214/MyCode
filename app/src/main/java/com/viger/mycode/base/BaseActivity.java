package com.viger.mycode.base;

import android.app.Activity;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    protected void startActivityForBase(Class<? extends Activity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityForBase2(Class<? super BaseActivity> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }



}
