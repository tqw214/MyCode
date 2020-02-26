package com.viger.mycode.login;

import android.text.TextUtils;

public class AccountManager{

    private static boolean isOnline;
    private static String token;

    public void setOnline(boolean isOnline){
        this.isOnline = isOnline;
    }

    public void setToken(String token){
        this.token = token;
    }

    public static boolean isOnline(){
        return isOnline && !TextUtils.isEmpty(token);
    }

    public String getToken(){
        return this.token;
    }

    public void logout(){
        this.isOnline = false;
        this.token = null;
    }
}
