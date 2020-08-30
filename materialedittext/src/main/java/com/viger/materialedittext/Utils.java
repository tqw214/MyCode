package com.viger.materialedittext;

import android.content.Context;
import android.util.TypedValue;

public class Utils {


    public static float dpToPixel(int i, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, context.getResources().getDisplayMetrics());
    }
}
