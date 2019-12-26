package com.viger.mycode.handle;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

    private LinkedBlockingQueue<Message> mQueue = new LinkedBlockingQueue<Message>();

    public void enqueue(Message message) {
        mQueue.add(message);
    }

    public Message next() {
        for(;;) {
            Message poll = mQueue.poll();
            if(poll != null) {
                return poll;
            }
        }
    }

}
