package com.viger.mycode.handle;

import android.os.SystemClock;

public class Handler {

    private Message message;
    private MessageQueue messageQueue;
    private Loop loop;

    public Handler() {
        loop = Loop.myLoop();
        messageQueue = loop.messageQueue;
    }


    public void sendMessage(Message message) {
       sendMessageAtTime(message, 0);
    }

    public void sendMessageAtTime(Message message, long delayMillis) {
        message.target = this;
        messageQueue.enqueueMessage(message, SystemClock.uptimeMillis() + delayMillis);
    }


    public void dispatchMessage(Message msg) {
        handlerMessage(msg);
    }

    public void handlerMessage(Message msg) {

    }


}
