package com.viger.binderclient;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.viger.mycode.aidl.IUserInfoAidl;

public class MainActivity extends AppCompatActivity {

    private IUserInfoAidl userInfoAidl;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("tag", "ServiceConnection====== onServiceConnected()");
            userInfoAidl = IUserInfoAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("tag", "ServiceConnection====== onServiceDisconnected()");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("com.viger.mycode.userinfo.aidl");
        intent.setPackage("com.viger.mycode");
        bindService(intent, connection, BIND_AUTO_CREATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast.makeText(MainActivity.this, userInfoAidl.getUserName() + "" + userInfoAidl.getPassword(), Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        },3000);

    }
}
