package com.landenlabs.all_UiDemo.Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.landenlabs.all_UiDemo.R;

/**
 * Created by Dennis Lang on 3/28/17.
 */

public class TextViewSpin extends TextView {

    private LinearInterpolator mInterpolator;
    private Animation mAnimation;
    private final int animResid = R.anim.rotate1;

    public TextViewSpin(Context context) {
        super(context);
        init(context, null, 0);
    }

    public TextViewSpin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public TextViewSpin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    @TargetApi(21)
    public TextViewSpin(Context context, AttributeSet attrs, int defStyleAttr,  int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
    }

    public boolean isAnimating() {
        return mAnimation != null;
    }

    public void startAnimation(){
        if (mInterpolator == null) {
            mInterpolator = new LinearInterpolator();
        }
        mAnimation = AnimationUtils.loadAnimation(getContext(), animResid);
        mAnimation.setInterpolator(mInterpolator);
        clearAnimation();
        setAnimation(mAnimation);
        mAnimation.start();
    }

    public void stopAnimation(){
        if(mAnimation != null){
            mAnimation = null;
            clearAnimation();
        }
    }

}