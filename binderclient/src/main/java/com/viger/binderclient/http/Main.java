package com.viger.binderclient.http;

public class Main {

    public static void get() {
        HttpUtils httpUtils = new HttpUtils();

        httpUtils.doGet("", null, new HttpCallBack<Person>() {
            @Override
            void onSuccess(Person person) {

            }

            @Override
            void onFailed(String t) {

            }
        });
    }


}
