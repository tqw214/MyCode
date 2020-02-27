package com.viger.mycode.login;

import android.content.Context;
import android.content.Intent;

/**
 * 执行需要登录权限的操作
 */
public class HandlerForLoginUtils {

    public static MyHandlerCallback callback;
    public static Context sContext;
    public static boolean isLogin = false;

    public static void operatorAfterLogin(Context context, MyHandlerCallback myHandlerCallback) {
        callback = myHandlerCallback;
        sContext = context;
        checkIsLogin();
    }

    private static void checkIsLogin() {
        if(isLogin) {
            callback.doSomethingAfterLogin();
        }else {
            Intent intent = new Intent(sContext, com.viger.mycode.activity.LoginActivity.class);
            sContext.startActivity(intent);
        }
    }

    public static void clear(){
        if (callback!=null){
            callback = null;
        }
        if (sContext!=null){
            sContext = null;
        }
    }

    public interface MyHandlerCallback {
        void doSomethingAfterLogin();
    }


}


