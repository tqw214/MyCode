package com.viger.mycode.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

public class ViewUtils {

    public static void inject(Activity activity){
        try {
            Field[] declaredFields = activity.getClass().getDeclaredFields();
            for(Field field : declaredFields) {
                ViewById annotation = field.getAnnotation(ViewById.class);
                if(annotation != null) {
                    View view = activity.findViewById(annotation.value());
                    field.setAccessible(true);
                    field.set(activity, view);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T findById(int resId, Activity activity) {
        return (T) activity.findViewById(resId);
    }

}
