package com.landenlabs.all_UiDemo.Util;

/*
 * Copyright (c) 2019 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (3/21/2015)
 *  @see http://landenlabs.com
 *
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.landenlabs.all_UiDemo.R;

/**
 * @author Dennis Lang (LanDen Labs)
 *
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

@SuppressWarnings("FieldCanBeLocal")
public class TextViewSpin extends androidx.appcompat.widget.AppCompatTextView {

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

    /*
    @TargetApi(21)
    public TextViewSpin(Context context, AttributeSet attrs, int defStyleAttr,  int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
     */

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