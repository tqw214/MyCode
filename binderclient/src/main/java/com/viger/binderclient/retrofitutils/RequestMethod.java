package com.viger.binderclient.retrofitutils;

enum RequestMethod {
    GET("GET"),POST("POST");

    private String value;

    RequestMethod(String name) {
        this.value = name;
    }

    public String getValue() {
        return value;
    }


}
