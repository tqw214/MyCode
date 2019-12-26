package com.viger.mycode.handle;

public class HandlerMain {

    private static Handler handler = new Handler() {
        @Override
        public void handlerMessage(Message msg) {
            super.handlerMessage(msg);
            System.out.println("message is " + msg.data);
        }
    };

    public static void main(String[] args) throws InterruptedException {
//        Handler handler = new Handler() ;
        Loop.prepare();
        Loop.loop();
        Thread.sleep(3000);
        Message message = new Message();
        message.tag = 0;
        message.data = "this is data";
        handler.sendMessage(message);
    }

}
