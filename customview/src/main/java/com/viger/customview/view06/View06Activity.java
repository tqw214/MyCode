package com.viger.customview.view06;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.R;

public class View06Activity extends AppCompatActivity {

    private LetterSideBar letterSideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view06);

        letterSideBar = findViewById(R.id.letterSideBar);
        letterSideBar.setOnLetterTouchListener(new LetterSideBar.LetterTouchListener(){
            @Override
            public void touch(CharSequence letter, boolean isTouch) {
                Toast.makeText(View06Activity.this, letter.toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
