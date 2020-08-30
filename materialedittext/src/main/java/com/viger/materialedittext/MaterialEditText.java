package com.viger.materialedittext;

import android.content.Context;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MaterialEditText extends androidx.appcompat.widget.AppCompatEditText {

    private float textSize;
    private float textMargin;

    public MaterialEditText(@NonNull Context context) {
        this(context, null);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        textSize = Utils.dpToPixel(12,getContext());
        textMargin = Utils.dpToPixel(8, getContext());
        setPadding(getPaddingLeft(), (int) (getPaddingTop() + textSize + textMargin), getPaddingRight(), getPaddingBottom());
    }
}
