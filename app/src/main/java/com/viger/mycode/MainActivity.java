package com.viger.mycode;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.viger.mycode.annotation.ViewById;
import com.viger.mycode.annotation.ViewUtils;
import com.viger.mycode.base.BaseActivity;
import com.viger.mycode.myglide.MyGlideActivity;
import com.viger.mycode.retrofit.Api;
import com.viger.mycode.utils.CheckNet;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends BaseActivity {

    //private Button btn_myglide;
    //private Disposable disposable;
    //private Intent intent = null;
    @ViewById(R.id.tv)
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        //btn_myglide = findViewById(R.id.btn_myglide);

        //retrofitAndRxJava();
        //doSomethingWithNetWork();
        //checkPermission();
        //Intent intent = new Intent(this, UserInfoService.class);
        //startService(intent);
        mTextView.setText("ioc显示的文字");
        //mTextView = ViewUtils.findById(R.id.tv, this);
        //startActivityForBase(MainActivity.class);
        //startActivityForBase2(Activity.class);
    }

    private void wirteFileToSdCard() throws Exception{
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File sdCardDir = Environment.getExternalStorageDirectory().getAbsoluteFile();//获取SDCard目录
            File saveFile = new File(sdCardDir, "a.txt");
            FileOutputStream outStream = new FileOutputStream(saveFile);
            outStream.write("test".getBytes());
            outStream.close();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.i("checkPermission","没有权限开始申请...");

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                     ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0);

                } else {
                    Log.i("checkPermission","requestPermissions");

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            0);

                }
            }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("onRequestPerm","权限申请到了....开始进行sd卡写入");
                    try {
                        wirteFileToSdCard();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("onRequestPerm","权限申请失败....");
                }
                return;
            }
        }
    }

    @CheckNet
    private void doSomethingWithNetWork() {
        System.out.println("有网络了，可以做事了。。。。。");
    }

    private void retrofitAndRxJava() {
        Retrofit retrofit = new Retrofit
                                .Builder()
                                .baseUrl("https://api.github.com/")
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        Api api = retrofit.create(Api.class);
        api.getResponse("username")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //disposable = d;
                    }

                    @Override
                    public void onSuccess(List<String> strings) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void click_myglide(View view) {
        Intent intent = new Intent(this, MyGlideActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //stopService(intent);

    }
}
