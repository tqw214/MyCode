package com.viger.mycode;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Fangshe {

    public Fangshe() throws InstantiationException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {

        Class<?> clazz = TestBean.class;
        Constructor<?> constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        TestBean object = (TestBean) constructor.newInstance();

        //System.out.println("getName() == " + object.getName());

        //System.out.println("getage() == " + object.getAge());
        Field name = clazz.getDeclaredField("name");
        name.setAccessible(true);
        String n = (String) name.get(object);
        System.out.println("fangshe == name : " + n);
        Method getName = clazz.getDeclaredMethod("getName");
        String result = (String) getName.invoke(object);
        System.out.println("getName() = " + result);

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for(Method method : declaredMethods) {
            CheckNet annotation = method.getAnnotation(CheckNet.class);
            if(annotation != null) {
                method.invoke(object, "我是传递的参数");
            }
        }


    }
}
