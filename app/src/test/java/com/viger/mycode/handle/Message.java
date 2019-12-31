package com.viger.mycode.handle;

public class Message {

    int tag;
    Object data;
    Handler target;
    long when;
    Message next;

    public boolean isAsynchronous() {
        return false;
    }
}
