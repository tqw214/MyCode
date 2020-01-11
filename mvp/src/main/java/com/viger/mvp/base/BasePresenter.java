package com.viger.mvp.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BasePresenter<V extends BaseView> {

    private WeakReference<V> mView;
    private V mProxyView;

    public void attach(V view) {
        this.mView = new WeakReference<V>(view);
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if(mView == null || mView.get() == null) {
                    return null;
                }
                return method.invoke(mView.get(), objects);
            }
        });
    }

    public void detach() {
        this.mView.clear();
        this.mView = null;
        this.mProxyView = null;
    }

    public V getView() {
        return this.mProxyView;
    }



}
