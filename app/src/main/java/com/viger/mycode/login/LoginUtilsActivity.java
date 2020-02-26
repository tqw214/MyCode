package com.viger.mycode.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.viger.mycode.activity.LoginActivity;

public class LoginUtilsActivity extends AppCompatActivity {

    private int REQUEST_CODE_LOGIN = 1;
    static LoginCallback mCallback;

    interface LoginCallback {
        void onLogin();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, REQUEST_CODE_LOGIN);
    }

    public static void checkLogin(Context context, LoginCallback callback) {
        //此处检查当前的登录状态
        boolean login = AccountManager.isOnline();
        if (login) {
            callback.onLogin();
        } else {
            mCallback = callback;
            Intent intent = new Intent(context, LoginUtilsActivity.class);
            context.startActivity(intent);
        }
    }

    public static void checkLogin(Context context, LoginCallback logged, LoginCallback callback) {
        //此处检查当前的登录状态
        boolean login = AccountManager.isOnline();
        if (login) {
            logged.onLogin();
        } else {
            mCallback = callback;
            Intent intent = new Intent(context, LoginUtilsActivity.class);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        finish();
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK && mCallback != null) {
            mCallback.onLogin();
        }
        mCallback = null;
    }

}
