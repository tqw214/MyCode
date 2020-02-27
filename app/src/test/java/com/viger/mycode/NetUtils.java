package com.viger.mycode;

import android.content.Context;

import com.viger.mycode.login.HandlerForLoginUtils;

public class NetUtils {



    public static void main(String[] args) {

        Context context = null;

        HandlerForLoginUtils.operatorAfterLogin(context, new HandlerForLoginUtils.MyHandlerCallback() {
            @Override
            public void doSomethingAfterLogin() {
                queryMyData();
            }
        });

    }

    //查询个人收藏（需要登录权限）
    private static void queryMyData() {

    }

}
