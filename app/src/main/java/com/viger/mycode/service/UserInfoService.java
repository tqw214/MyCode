package com.viger.mycode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.viger.mycode.aidl.IUserInfoAidl;

public class UserInfoService extends Service {

    private IUserInfoAidl.Stub mBinder;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("tag", "UserInfoService =====  onCreate()");
        mBinder = new IUserInfoAidl.Stub() {

            @Override
            public String getUserName() throws RemoteException {
                return "viger";
            }

            @Override
            public String getPassword() throws RemoteException {
                return "123456";
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("tag", "UserInfoService =====  onBind()");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("tag", "UserInfoService =====  onDestroy()");

    }




}
