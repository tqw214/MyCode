package com.viger.mycode.utils;


import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import java.lang.ref.WeakReference;

public class HandlerUtils {

    private static volatile HandlerUtils sInstance = null;
    private Handler mHandler = null;
    private OnHandlerCallBack callBack;
    private WeakReference<Activity> weakReference;

    private HandlerUtils(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Activity act = weakReference.get();
                if(act != null) {
                    callBack.onResult(msg);
                }
            }
        };
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public static HandlerUtils getInstance(Activity activity) {
        if(sInstance == null) {
            synchronized (HandlerUtils.class) {
                if(sInstance == null) {
                    sInstance = new HandlerUtils(activity);
                }
            }
        }
        return sInstance;
    }

    public HandlerUtils setCallBack(OnHandlerCallBack callBack) {
        this.callBack = callBack;
        return this;
    }


    public interface OnHandlerCallBack {
        void onResult(Message msg);
    }

}
