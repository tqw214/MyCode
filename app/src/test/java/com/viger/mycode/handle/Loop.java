package com.viger.mycode.handle;

public class Loop {

    private static final ThreadLocal<Loop> mThreadLocalForLoop = new ThreadLocal<Loop>();
    public static MessageQueue messageQueue;

    private Loop() {
        messageQueue = new MessageQueue();
    }

    public static void prepare() {
        Loop loop = mThreadLocalForLoop.get();
        if(loop != null) {
            throw new RuntimeException("");
        }
        mThreadLocalForLoop.set(new Loop());
    }


    public static Loop myLoop() {
        return mThreadLocalForLoop.get();
    }

    public static void loop() {
        for(;;) {
            Message next = messageQueue.next();
            if(next != null) {
                next.target.dispatchMessage(next);
            }
        }
    }


}
