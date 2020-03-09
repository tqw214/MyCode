package com.viger.mycode.hook;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.lang.reflect.Method;

public class HookActivityManager {

    private void hook() {

    }

    private void getResource(Context context) {
        Resources resources = context.getResources();
        Class<AssetManager> assetManagerClass = AssetManager.class;
        AssetManager assetManager = null;
        try {
            //创建AssetManager
            assetManager = assetManagerClass.newInstance();
            //反射执行addAssetPath方法，添加资源所在路径
            Method addAssetPath = assetManagerClass.getDeclaredMethod("addAssetPath", String.class);
            addAssetPath.setAccessible(true);
            addAssetPath.invoke(assetManager, "");
            Resources mSkinResource = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
