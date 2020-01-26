package com.viger.binderclient.aop;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.binderclient.R;

public class AopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop);
    }

    //摇一摇
    @BehaviorTrace("摇一摇")
    @UserInfoBehaviorTrace("摇一摇")
    public void mShake(View view) {
//        long begin = System.currentTimeMillis();
//        SystemClock.sleep(new Random().nextInt(2000));
//        long duration = System.currentTimeMillis() - begin;
        Log.d("tag","AopActivity:mShake=> ");
        String result = test();
        Log.d("tag","result=> "+result);
    }

    @BehaviorTrace("test")
    @UserInfoBehaviorTrace("test")
    public String test() {
        Log.d("tag","test()=> ");
        return "123";
    }

    //语音消息
    @BehaviorTrace("语音消息")
    @UserInfoBehaviorTrace("语音消息")
    public void mAudio(View view) {
        Log.d("tag","AopActivity:mAudio=> ");
    }

    //视频通话
    @BehaviorTrace("视频通话")
    public void mVideo(View view) {

    }

    //发表说说
    @BehaviorTrace("发表说说")
    public void saySomething(View view) {

    }

}
