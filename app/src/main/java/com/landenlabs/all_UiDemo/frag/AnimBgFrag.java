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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.ALog.ALog;
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

    private TextProgressBar mTextProgress1;
    private TextViewSpin mTextProgress2;
    private TextViewSpin mTextProgress3;

    private final AnimationState state1 = new AnimationState();
    private final AnimationState state2 = new AnimationState();

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

    @Override
    public void onDetach() {
        state1.stop();
        state2.stop();
        super.onDetach();
    }

    private void setup() {
        setupHorizontalAnimation(state1, R.id.anim_bg_image1, R.id.anim_tx_image1);
        setupVerticalAnimation(state2, R.id.anim_bg_image2, R.id.anim_tx_image2);
        
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

    
    Drawable getImage(ImageView imageView) {
        return imageView.getDrawable();
    }

    private void setupHorizontalAnimation(AnimationState state, int imageRes,  int textRes) {
        ImageView imageView = Ui.viewById(mRootView, imageRes);
        TextView textView =  Ui.viewById(mRootView, textRes);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getX() < view.getWidth()/4) {
                    animate(state, Direction.ToLeft);
                } else if (event.getX() > view.getWidth()*3/4) {
                    animate(state, Direction.ToRight);
                }
                return false;
            }
        });

        imageView.post(new Runnable() {
            @Override
            public void run() {
                Matrix matrix = new Matrix();

                // centerMatrix(mImageView1, matrix);
                float scale = 1f;
                if (getImage(imageView).getIntrinsicWidth() < imageView.getWidth() * 1.5) {
                    scale =  (imageView.getWidth() * 2f) / getImage(imageView).getIntrinsicWidth();
                    matrix.postScale(scale, scale);
                }

                imageView.setImageMatrix(matrix);

                animate(state, Direction.ToLeft, 5000, false, imageView);
                textView.setText(String.format("Img w=%d View w=%d scale=%.1f",
                        getImage(imageView).getIntrinsicWidth(), imageView.getWidth(), scale));
            }
        });
    }
    
    private void setupVerticalAnimation(AnimationState state, int imageRes, int textRes) {
        ImageView imageView  = Ui.viewById(mRootView, imageRes);
        TextView textView =  Ui.viewById(mRootView, textRes);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getY() < view.getHeight()/4) {
                    animate(state, Direction.ToTop);
                } else if (event.getY() > view.getHeight()*3/4) {
                    animate(state, Direction.ToBottom);
                }
                return false;
            }
        });

        imageView.post(new Runnable() {
            @Override
            public void run() {
                Matrix matrix = new Matrix();

                // Scale image to fill view by more than 50%
                float scale = 1f;
                if (getImage(imageView).getIntrinsicHeight() < imageView.getHeight()*1.5) {
                    scale =  getImage(imageView).getIntrinsicHeight() / (imageView.getHeight() * 2f) ;
                    matrix.postScale(scale, scale);
                }

                imageView.setImageMatrix(matrix);
                animate(state, Direction.ToBottom, 5000, true, imageView);
                textView.setText(String.format("Img h=%d View h=%d scale=%.1f",
                        getImage(imageView).getIntrinsicHeight(), imageView.getHeight(), 1f/scale));
            }
        });
    }

    // =============================================================================================
    // Animate assumes source image is larger than imageView canvas (portal).
    private void animate( AnimationState state, Direction direction, int durationMilli, boolean doRev, ImageView imageView) {
        state.direction = direction;
        state.durationMilli = durationMilli;
        state.doRev = doRev;
        state.imageView = imageView;
        animate(state);
    }
    private void animate( AnimationState state, Direction direction, int durationMilli, boolean doRev) {
        state.direction = direction;
        state.durationMilli = durationMilli;
        state.doRev = doRev;
        animate(state);
    }
    private void animate( AnimationState state, Direction direction, int durationMilli) {
        state.direction = direction;
        state.durationMilli = durationMilli;
        animate(state);
    }
    private void animate( AnimationState state, Direction direction) {
        state.direction = direction;
        animate(state);
    }

    private void animate( AnimationState state) {
        // imageView.getImageMatrix().mapRect(rawSourceRect);

        float[] f = new float[9];
        state.imageView.getImageMatrix().getValues(f);
        float scaleX = f[Matrix.MSCALE_X];
        float scaleY = f[Matrix.MSCALE_Y];

        float srcWidth = getImage(state.imageView).getIntrinsicWidth();
        float srcHeight = getImage(state.imageView).getIntrinsicHeight();
        state.srcRect = new RectF(0, 0,  srcWidth, srcHeight);

        switch (state.direction) {
            case ToLeft:
                animate(state,  0f, state.imageView.getWidth()/scaleX  -  srcWidth);
                break;
            case ToRight:
                animate(state, state.imageView.getWidth()/scaleX  -  srcWidth, 0f);
                break;
            case ToTop:
                animate(state,  0f,  state.imageView.getHeight() - srcHeight);
                break;
            case ToBottom:
                animate(state,  state.imageView.getHeight()  - srcHeight, 0f);
                break;
        }
    }

    private void animate(final AnimationState state,   final float from, final float to) {
        ALog.d.tagFmt(this,  state.direction.toString() + " From %.1f To %.1f Width=%.1f", from, to, state.srcRect.width());
        if (from != to) {
            state.stop();
            state.animator = ValueAnimator.ofFloat(from, to);
            state.animator.setInterpolator(new LinearInterpolator());
            state.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (Float) animation.getAnimatedValue();

                    Matrix matrix = state.imageView.getImageMatrix(); //
                    float[] f = new float[9];
                    state.imageView.getImageMatrix().getValues(f);
                    float scaleX = f[Matrix.MSCALE_X];
                    float scaleY = f[Matrix.MSCALE_Y];

                    //matrix.reset();
                    matrix = new Matrix();
                    float x = state.direction.getX(value, state.srcRect);
                    float y = state.direction.getY(value, state.srcRect);
                    matrix.postTranslate(x, y);
                    matrix.postScale(scaleX, scaleY);
                    // ALog.d.tagMsg(this, String.format(" val=%.0f  x=%.0f, y=%.0f w=%.0f", value, x, y, displayRect.width()));

                    state.imageView.setImageMatrix(matrix);
                }
            });

            state.animator.setDuration(state.durationMilli);
            state.animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // animation.cancel();
                    // animation.removeAllListeners();
                    if (state.doRev) {
                        animate(state, state.direction.reverse());
                    } else {
                        animate(state, from, to);
                    }
                }
            });
            state.animator.start();
        }
    }


    // =============================================================================================
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

    // =============================================================================================
    static class AnimationState {
        ValueAnimator animator;
        Direction direction;
        int durationMilli;
        boolean doRev;
        ImageView imageView;
        RectF srcRect;

        void stop() {
            if (animator != null) {
                animator.cancel();
                animator.removeAllListeners();
                animator = null;
            }
        }
    }

}
