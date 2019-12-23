package com.viger.mycode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.mycode.myglide.MyGlideActivity;
import com.viger.mycode.retrofit.Api;
import com.viger.mycode.utils.CheckNet;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btn_myglide;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btn_myglide = findViewById(R.id.btn_myglide);

        //retrofitAndRxJava();
        doSomethingWithNetWork();
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
                        disposable = d;
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
        if(!disposable.isDisposed())
            disposable.dispose();
    }
}
