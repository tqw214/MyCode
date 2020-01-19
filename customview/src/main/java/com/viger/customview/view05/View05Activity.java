package com.viger.customview.view05;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.R;

public class View05Activity extends AppCompatActivity {

    private MyProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view05);

        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setMax(4000);
        progress_bar.setProgress(3000);


    }
}
