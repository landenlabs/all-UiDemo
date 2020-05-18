package com.landenlabs.all_UiDemo.frag;

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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;
import com.landenlabs.all_UiDemo.Util.TextProgressBar;
import com.landenlabs.all_UiDemo.Util.TextViewSpin;

/**
 * Demonstrate Animated Background (texture)
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

@SuppressWarnings("ALL")
public class AnimBgFrag  extends UiFragment implements View.OnClickListener {

    private View  mRootView;


    // http://stackoverflow.com/questions/27671653/background-animation-with-repeat

    // https://github.com/flavienlaurent/PanningView

    // http://flavienlaurent.com/blog/2013/08/05/make-your-background-moving-like-on-play-music-app/
    enum Direction {
        ToLeft {
            @Override
            public Direction reverse() {
                return ToRight;
            }
            @Override
            public float getX(float value, RectF displayRect) {
                return value % displayRect.width();
            }
        },
        ToRight{
            @Override
            public Direction reverse() {
                return ToLeft;
            }
            @Override
            public float getX(float value, RectF displayRect) {
                return value % displayRect.width();
            }
        },
        ToBottom{
            @Override
            public Direction reverse() {
                return ToTop;
            }
            @Override
            public float getY(float value, RectF displayRect) {
                return value % displayRect.height();
            }
        },
        ToTop{
            @Override
            public Direction reverse() {
                return ToBottom;
            }
            @Override
            public float getY(float value, RectF displayRect) {
                return value % displayRect.height();
            }
        };

        protected abstract Direction reverse();
        float getX(float value, RectF displayRect) {
            return 0;
        }
        float getY(float value, RectF displayRect) {
            return 0;
        }
    }
    // private static final int ToLeft = 1;
    // private static final int ToRight = 2;

    private ImageView mImageView1;
    private ImageView mImageView2;
    @SuppressWarnings("unused")
    private float mScaleFactor;

    private TextProgressBar mTextProgress1;
    private TextViewSpin mTextProgress2;
    private TextViewSpin mTextProgress3;

    // =============================================================================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.anim_bg_frag, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.anim_bg_id;
    }

    @Override
    public String getName() {
        return "AnimBg";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            default:
                break;
        }
    }

    private void setup() {
        mImageView1 = Ui.viewById(mRootView, R.id.anim_bg_image1);
        mImageView1.post(new Runnable() {
            @Override
            public void run() {
                mScaleFactor = (float)  mImageView1.getHeight() / (float) mImageView1.getDrawable().getIntrinsicHeight();
                Matrix matrix = new Matrix();
                // mMatrix.postScale(mScaleFactor, mScaleFactor);
                mImageView1.setImageMatrix(matrix);
                animate(mImageView1, Direction.ToLeft, 15000, false);
            }
        });

        mImageView2 = Ui.viewById(mRootView, R.id.anim_bg_image2);
        mImageView2.post(new Runnable() {
            @Override
            public void run() {
                mScaleFactor = (float)  mImageView2.getHeight() / (float) mImageView2.getDrawable().getIntrinsicHeight();
                Matrix matrix = new Matrix();
                // matrix.postScale(mScaleFactor, mScaleFactor);
                mImageView2.setImageMatrix(matrix);
                animate(mImageView2, Direction.ToTop, 5000, true);
            }
        });


        mTextProgress1 = Ui.viewById(mRootView, R.id.textProgressBar1);
        mTextProgress1.setText("123");

        mTextProgress2 = Ui.viewById(mRootView, R.id.textProgressBar2);
        mTextProgress3 = Ui.viewById(mRootView, R.id.textProgressBar3);

        Ui.viewById(mRootView, R.id.anim_progress_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextProgress2.isAnimating()) {
                    mTextProgress2.stopAnimation();
                    mTextProgress2.setText("12");
                    mTextProgress2.setBackgroundResource(R.drawable.badge_bg);
                } else {
                    mTextProgress2.startAnimation();
                    mTextProgress2.setText(" ");
                    mTextProgress2.setBackgroundResource(R.drawable.badge_spin);
                }

                if (mTextProgress3.isAnimating())
                    mTextProgress3.stopAnimation();
                else
                    mTextProgress3.startAnimation();
            }
        });
    }

    private void animate(ImageView imageView, Direction direction, final int durationMilli, boolean doRev) {
        RectF displayRect = new RectF(0, 0,
                imageView.getDrawable().getIntrinsicWidth(), imageView.getDrawable().getIntrinsicHeight());
        imageView.getImageMatrix().mapRect(displayRect);

        switch (direction) {
            case ToLeft:
                animate(imageView, direction, durationMilli, doRev, displayRect,
                    displayRect.left, imageView.getWidth() + displayRect.left - displayRect.right);
                break;
            case ToRight:
                animate(imageView, direction, durationMilli, doRev, displayRect, displayRect.left, 0.0f);
                break;

            case ToTop:
                animate(imageView, direction, durationMilli, doRev, displayRect,
                        displayRect.top, imageView.getHeight() + displayRect.top - displayRect.bottom);
                break;
            case ToBottom:
                animate(imageView, direction, durationMilli, doRev, displayRect, displayRect.top, 0.0f);
                break;
        }
    }

    private void animate(final ImageView imageView,
             final Direction direction, final int durationMilli, final boolean doRev,
             final RectF displayRect,
             final float from, final float to) {
        ValueAnimator animator;
        animator = ValueAnimator.ofFloat(from, to);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();

                Matrix matrix = new Matrix(); // imageView.getImageMatrix();
                matrix.reset();
                // matrix.postScale(mScaleFactor, mScaleFactor);
                float x = direction.getX(value, displayRect);
                float y = direction.getY(value, displayRect);
                matrix.postTranslate(x, y);
                // ALog.d.tagMsg(this,  String.format("x=%.0f, y=%.0f w=%.0f", x, y, displayRect.width()));

                imageView.setImageMatrix(matrix);
            }
        });

        animator.setDuration(durationMilli);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (doRev) {
                    animate(imageView, direction.reverse(), durationMilli, true);
                } else {
                    animate(imageView, direction, durationMilli, false, displayRect, from, to);
                }
            }
        });
        animator.start();
    }

}
