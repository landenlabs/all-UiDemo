/*
 * Copyright (c) 2020 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author Dennis Lang
 * @see https://LanDenLabs.com/
 */

package com.landenlabs.all_UiDemo.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.core.content.res.ResourcesCompat;

import com.landenlabs.all_UiDemo.R;


/**
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://LanDenLabs.com/android"> author's web-site </a>
 */

public class CompatSeekBar extends AppCompatSeekBar {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    private int mTickColor = 0xc0c08080;
    private float mMinTic = 0;
    private float mMaxTic = getMax();
    private float mTickStep = 10;
    private float mTickWidth = 20;
    private boolean mTickUnder = false;

    private Drawable mTickMarkDr;


    public CompatSeekBar(Context context) {
        super(context);
        initCompSeekkBar(null, 0);
    }

    /*
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CompatSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initCompSeekkBar(attrs, defStyleAttr);
    }
    */

    public CompatSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCompSeekkBar(attrs, defStyleAttr);
    }

    public CompatSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompSeekkBar(attrs, 0);
    }

    public void setTickStep(float tickStep) {
        if (mTickStep != tickStep) {
            mTickStep = tickStep;
            invalidate();
        }
    }

    public void setTickWidth(float tickWidth) {
        if (mTickWidth != tickWidth) {
            mTickWidth = tickWidth;
            invalidate();
        }
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        // super.setSecondaryProgress(progress);
    }

    private void initCompSeekkBar(AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode())
            return;
        // mMaxTic = getMax();

        TypedArray a = getContext().obtainStyledAttributes(attrs,
                R.styleable.CompatSeekBar, defStyleAttr,
                android.R.style.Widget_SeekBar);

        mMinTic = a.getInt(R.styleable.CompatSeekBar_tickMin, 0);
        mMaxTic = a.getInt(R.styleable.CompatSeekBar_tickMax, getMax());
        mTickStep = a.getInt(R.styleable.CompatSeekBar_tickStep, getMax());

        mTickWidth = a.getInt(R.styleable.CompatSeekBar_tickWidth, 20);
        mTickUnder = a.getBoolean(R.styleable.CompatSeekBar_tickUnder, false);

        int tickMark = a.getResourceId(R.styleable.CompatSeekBar_tickMarker, -1);
        int defColor = (tickMark == -1) ? 0xff101010 : 0;
        mTickColor = a.getColor(R.styleable.CompatSeekBar_tickColor, defColor);

        a.recycle();

        if (tickMark != -1) {
            mTickMarkDr = ResourcesCompat.getDrawable(getResources(), tickMark, getContext().getTheme());
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //  isInEditMode()

        if (mTickUnder)
            drawTicks(canvas);

        super.onDraw(canvas);

        if (!mTickUnder)
            drawTicks(canvas);
    }

    private void drawTicks(Canvas canvas) {

        if (mTickWidth > 0 && (mTickColor != 0 || mTickMarkDr != null)) {
            float dX = mTickWidth / 2;
            float ticHeight = getHeight();

            int pixelWidth = (getWidth() - getPaddingLeft() - getPaddingRight());
            float per1 = mMinTic / getMax();
            float per2 = mMaxTic / getMax();
            float perStep =  mTickStep / getMax();


            if (perStep > 0) {
                for (float xPer = per1; xPer < per2; xPer += perStep) {
                    float x = xPer * pixelWidth + getPaddingLeft();
                    drawTick(canvas, new RectF(x - dX, 0, x + dX, ticHeight), paint);
                }
            }

            float x = per2 * pixelWidth + getPaddingLeft();
            drawTick(canvas, new RectF(x - dX, 0, x + dX, ticHeight), paint);
        }
    }

    private void drawTick(Canvas canvas, RectF rectF, Paint paint) {
        if (mTickMarkDr == null) {
            if (mTickColor != 0) {
                paint.setColor(mTickColor);
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            }
            canvas.drawRect(rectF, paint);
            // canvas.drawOval(rectF, paint);
        } else {
            mTickMarkDr.setBounds((int)rectF.left, (int)rectF.top, (int)rectF.right, (int)rectF.bottom);
            if (mTickColor != 0) {
                // mTickMarkDr.setColorFilter(new PorterDuffColorFilter(mTickColor, PorterDuff.Mode.SRC_OVER));
                mTickMarkDr.setColorFilter(new PorterDuffColorFilter(mTickColor, PorterDuff.Mode.MULTIPLY));
            }
            mTickMarkDr.draw(canvas);
        }
    }
}
