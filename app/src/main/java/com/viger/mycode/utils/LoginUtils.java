package com.viger.mycode.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.viger.mycode.activity.LoginActivity;

/**
 * 集中处理登录逻辑
 */
public class LoginUtils {

    private static volatile LoginUtils instance;

    private LoginUtils(){

    }

    public static LoginUtils getInstance() {
        if(instance == null) {
            synchronized (LoginUtils.class){
                if(instance == null) {
                    instance = new LoginUtils();
                }
            }
        }
        return instance;
    }

    //跳转路由
    public void startActivity(Context context, Class targetCalss) {
        startActivity(context, targetCalss, null);
    }

    /**
     * @param context
     * @param targetCalss
     * @param bundle
     */
    public void startActivity(Context context, Class targetCalss, Bundle bundle) {
        Intent intent = new Intent(context, targetCalss);
        if(bundle != null) {
            bundle.putString("target",targetCalss.getName());
            intent.putExtra("data", bundle);
        }
        //判断是否登录
        if(isLogin()) {
            context.startActivity(intent, bundle);
        }else {
            startLoginActivity(context);
        }

    }

    private void startLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private boolean isLogin() {
        return false;
    }

//    public void startActivityForResult(Context context, Class targetCalss, Bundle bundle) {
//        Intent intent = new Intent(context, targetCalss);
//
//    }

}
