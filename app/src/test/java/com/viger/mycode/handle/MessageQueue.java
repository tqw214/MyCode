package com.viger.mycode.handle;

import android.os.SystemClock;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

    private LinkedBlockingQueue<Message> mQueue = new LinkedBlockingQueue<Message>();
    Message mMessages;
    private boolean mBlocked;

    boolean enqueueMessage(Message msg, long when) {
        synchronized (this) {
            msg.when = when;
            Message p = mMessages;
            if(p == null || when == 0 || when < p.when) {
                msg.next = p;
                mMessages = msg;
            }else {
                Message prev;
                for(;;) {
                    prev = p;
                    p = p.next;
                    if(p == null && when < p.when) {
                        //找到插入点了
                        break;
                    }
                }
                prev.next = msg;
                msg.next = p;
            }
        }
        return true;
    }

    Message next() {
        for(;;) {
           //nativePollOnce(ptr, nextPollTimeoutMillis); 阻塞时间差再处理
            synchronized (this) {
                long now = SystemClock.uptimeMillis();
                Message prevMsg = null;
                Message msg = mMessages;
                if(msg != null && msg.target == null) {//如果有消息屏障，则先找到异步方法处理，同步方法等待。
                    //消息屏障，这个屏障之后的所有同步消息都不会被执行，即使时间已经到了也不会执行。
                    do {
                       prevMsg = msg;
                       msg = msg.next;
                    }while (msg != null && !msg.isAsynchronous()); //!msg.isAsynchronous()异步方法
                }
                if(msg != null) {
                    if(now < msg.when) {
                        int nextPollTimeoutMillis = (int) Math.min(msg.when - now, Integer.MAX_VALUE);
                    }else {
                        mBlocked = false; //代表目前没有阻塞
                        if (prevMsg != null) {
                            prevMsg.next = msg.next;
                        } else {
                            mMessages = msg.next;
                        }
                        msg.next = null;
                        return msg;
                    }
                }
            }
        }
    }

}
