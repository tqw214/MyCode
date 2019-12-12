package com.viger.mycode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.mycode.myglide.MyGlideActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn_myglide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_myglide = findViewById(R.id.btn_myglide);
    }

    public void click_myglide(View view) {
        Intent intent = new Intent(this, MyGlideActivity.class);
        startActivity(intent);
    }

}
