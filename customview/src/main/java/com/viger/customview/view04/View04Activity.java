package com.viger.customview.view04;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.R;

public class View04Activity extends AppCompatActivity {

    private FlowerLayout flowerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_04);
        flowerLayout = findViewById(R.id.flowerLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textView1 = new TextView(this);
        textView1.setText("哈哈哈");
        textView1.setLayoutParams(params);


        TextView textView2 = new TextView(this);
        textView2.setText("粉色峰会上符合是否合适");
        textView2.setLayoutParams(params);

        TextView textView3 = new TextView(this);
        textView3.setText("粉色方式");
        textView3.setLayoutParams(params);

        TextView textView4 = new TextView(this);
        textView4.setText("粉色方式");
        textView4.setLayoutParams(params);

        TextView textView5 = new TextView(this);
        textView5.setText("粉色方式");
        textView5.setLayoutParams(params);

        TextView textView6 = new TextView(this);
        textView6.setText("粉色方式");
        textView6.setLayoutParams(params);

        flowerLayout.addView(textView1);
        flowerLayout.addView(textView2);
        flowerLayout.addView(textView3);
        flowerLayout.addView(textView4);
        flowerLayout.addView(textView5);
        flowerLayout.addView(textView6);
    }
}
