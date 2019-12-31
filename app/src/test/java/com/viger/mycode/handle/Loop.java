package com.viger.mycode.handle;

public class Loop {

    private static final ThreadLocal<Loop> mThreadLocalForLoop = new ThreadLocal<Loop>();
    public MessageQueue messageQueue;

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
        final Loop me = myLoop();
        if(me == null) {
            throw new RuntimeException("no looper");
        }
        MessageQueue queue = me.messageQueue;
        for(;;) {
            Message msg = queue.next();// 阻塞线程
            if(msg == null) {
                return;
            }
            if(msg != null) {
                msg.target.dispatchMessage(msg);
            }
        }
    }


}
