package com.viger.mycode.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookSetOnClickListenerHelper {

    public static void hook(Context context, final View v) throws Exception {
        Method getListenerInfo = v.getClass().getDeclaredMethod("getListenerInfo");
        getListenerInfo.setAccessible(true);
        Object listenerInfo = getListenerInfo.invoke(v);
        Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
        Field mOnClickListenerField = listenerInfoClass.getDeclaredField("mOnClickListener");
        mOnClickListenerField.setAccessible(true);
        View.OnClickListener onClickListener = (View.OnClickListener) mOnClickListenerField.get(listenerInfo);

        Object proxyOnClickListener = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d("HookSetOnClickListener", "点击事件被hook到了");//加入自己的逻辑
                return method.invoke(onClickListener, args);
            }
        });

        mOnClickListenerField.set(listenerInfo, proxyOnClickListener);

    }

    // 还真是这样,自定义代理类
    static class ProxyOnClickListener implements View.OnClickListener {
        View.OnClickListener oriLis;

        public ProxyOnClickListener(View.OnClickListener oriLis) {
            this.oriLis = oriLis;
        }

        @Override
        public void onClick(View v) {
            Log.d("HookSetOnClickListener", "点击事件被hook到了");
            if (oriLis != null) {
                oriLis.onClick(v);
            }
        }
    }


}
