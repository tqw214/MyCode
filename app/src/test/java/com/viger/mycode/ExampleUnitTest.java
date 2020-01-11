package com.viger.mycode;

import android.os.MessageQueue;

import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    ThreadLocal t;
    Thread t1;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test01() {
//        int num1 = 1;
//        int num2 = 1;
//        int result = 0;
//        int n = 6;
//        for(int i=3;i<=n;i++) {
//            result = num1+num2;
//            num1 = num2;
//            num2 = result;
//        }
//        System.out.println("this result = " + result);

        //int result = add(6);
        //System.out.println("this result = " + result);

        //files(new File("D:\\BaiduYunDownload"));

       // paixu01();

        //testHttp();

    }

    @Test
    public void testFangshe() throws IllegalAccessException, NoSuchFieldException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        new Fangshe();
    }


//   1000 = 8
    @Test
    public void testAop() {
        int i = 2 << 2;
        System.out.println(i);
        System.out.println(Integer.toBinaryString(i));

    }

    private void testMessageObtain() {

        HashMap h;

//        //private static Message sPool
//            if (sPool != null) {
//                Message m = sPool;
//                sPool = m.next;
//                m.next = null;
//                m.flags = 0;
//                sPoolSize--;
//                return m;
//            }
//        //return new Message();
    }

    @CheckNet
    private void showAopMessage() {
        System.out.println("showAopMessage");
    }

    @Test
    public void testHttp() {
        HttpUtils.init(new VolleyRequest());
        HttpUtils.getInstance().get("", null, new HttpUtils.HttpCallBack<ResultBean>() {
            @Override
            public void success(ResultBean resultBean) {

            }

            @Override
            public void failed(String message) {

            }
        });
    }

    public void paixu01() {
        int[] array = {5,3,7,8,2,9,4,6};
    }

    public int add(int n) {
        System.out.println("n = " + n);
        if(n == 1 || n== 2) {
            return 1;
        }else {
            return add(n-1) + add(n-2);
        }
    }

    public void files(File file) {
        File[] files = file.listFiles();
        for (File f : files) {
            if(f.isFile()) {
                System.out.println(f.getName());
            }else {
                files(f);
            }
        }
    }

    class ResultBean {
        String name;
        String value;
    }

    @Test
    public void test002() {
        ThreadPoolExecutor threadPoolExecutor;
        MessageQueue messageQueue;
        ThreadLocal local;
        System.out.println(1<<1);
        ArrayList list;
        LinkedList ll;
        ArrayDeque de;
    }




}