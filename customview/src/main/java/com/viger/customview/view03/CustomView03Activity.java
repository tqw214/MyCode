package com.viger.customview.view03;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.viger.customview.R;

public class CustomView03Activity extends AppCompatActivity {

    private ColorTrackTextView mColorTrackTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_03);
        mColorTrackTextView = (ColorTrackTextView) findViewById(R.id.color_track_tv);

    }

    public void leftToRight(View view) {
        Animation animation = mColorTrackTextView.getAnimation();
        animation.cancel();
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mColorTrackTextView.setCurrentProgress(animatedValue);
            }
        });
        valueAnimator.start();
    }

    public void rightToLeft(View view) {
        Animation animation = mColorTrackTextView.getAnimation();
        animation.cancel();
        mColorTrackTextView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                mColorTrackTextView.setCurrentProgress(animatedValue);
            }
        });
        valueAnimator.start();
    }

}
