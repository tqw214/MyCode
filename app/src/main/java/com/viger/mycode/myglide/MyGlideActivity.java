package com.viger.mycode.myglide;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.viger.mycode.R;
import com.viger.mycode.utils.ToastUtil;

public class MyGlideActivity extends AppCompatActivity {

    private static final String TAG = MyGlideActivity.class.getSimpleName();
    private Button btn_load_image;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myglide);
        btn_load_image = findViewById(R.id.btn_load_image);
        imageView = findViewById(R.id.imageView);
    }

    public void loadImage(View v) {
        ToastUtil.showShort(this,"加载图片...");

        MyGlide
                .with(this)
                .load("https://www.baidu.com/img/bd_logo1.png")
                .placeholder(R.mipmap.ic_launcher)
                .listener(new RequestListener() {
                    @Override
                    public void onResourceReady(Bitmap bitmap) {
                        Log.d(TAG, "加载图片成功....");
                    }

                    @Override
                    public void onLoadFailed(GlideException e) {
                        Log.d(TAG, "加载图片失败:"+e.getMessage());
                    }
                }).into(imageView);

    }

}
