package com.viger.mycode.handle;

public class Handler {

    private Message message;
    private MessageQueue messageQueue;
    private Loop loop;

    public Handler() {
        loop = Loop.myLoop();
        messageQueue = loop.messageQueue;
    }


    public void sendMessage(Message message) {
        message.target = this;
        messageQueue.enqueue(message);
    }

    public void dispatchMessage(Message msg) {
        handlerMessage(msg);
    }

    public void handlerMessage(Message msg) {

    }


}
