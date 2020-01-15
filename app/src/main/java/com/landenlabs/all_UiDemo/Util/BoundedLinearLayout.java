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
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.landenlabs.all_UiDemo.R;

/**
 * A LinearLayout which can resize up to a maximum height or width.
 *
 * <pre>
 * Example:
 *      // Children bound to a maximum height of 20dp
 *      &lt;com.wsi.android.weather.ui.widget.BoundedLinearLayout
 *          android:layout_width="wrap_content"
 *          android:layout_height="0dp"
 *          android:layout_weight="1"
 *          android:orientation="vertical"
 *          app:bounded_height="20dp">
 *          &lt;TextView
 *              android:layout_width="wrap_content"
 *              android:layout_height="match_parent" />
 *      &lt;/com.wsi.android.weather.ui.widget.BoundedLinearLayout>
 *
 *      // TextView allowed to grow in both directions but bounded width
 *      // first to 80% of parent and no more then 200dp
 *      &lt;com.wsi.android.weather.ui.widget.BoundedLinearLayout
 *          android:layout_width="wrap_contents"
 *          android:layout_height="wrap_contents"
 *          app:widthPercent="0.8"
 *          app:width="200dp">
 *          &lt;TextView
 *              android:layout_width="wrap_content"
 *              android:layout_height="wrap_content" />
 *      &lt;/com.wsi.android.weather.ui.widget.BoundedLinearLayout>
 * </pre>
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */
public class BoundedLinearLayout extends LinearLayout {

    private int mBoundedWidthPixel;       // max width
    private int mBoundedHeightPixel;      // max height
    private float mBoundedWidthPercent;
    private float mBoundedHeightPercent;
    private boolean mEnabled;
    private float mAspectWdivH;
    private int mIncludeLayoutResId;

    private static final int MAX_INT = Integer.MAX_VALUE;

    public BoundedLinearLayout(Context context) {
        super(context);
        mBoundedWidthPixel = MAX_INT;
        mBoundedHeightPixel = MAX_INT;
        mBoundedWidthPercent = 1;
        mBoundedHeightPercent = 1;
        mEnabled = true;
        mAspectWdivH = 0;           // 0=not set.
        mIncludeLayoutResId = -1;   // -1=not set.
    }

    public BoundedLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        init(context, attrs, defStyle);
    }

    public BoundedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedLinearLayout, defStyle, MAX_INT);
        mBoundedWidthPixel = a.getDimensionPixelSize(R.styleable.BoundedLinearLayout_width, MAX_INT);
        mBoundedHeightPixel = a.getDimensionPixelSize(R.styleable.BoundedLinearLayout_height, MAX_INT);
        mBoundedWidthPercent = a.getFloat(R.styleable.BoundedLinearLayout_widthPercent, 1);
        mBoundedHeightPercent = a.getFloat(R.styleable.BoundedLinearLayout_heightPercent, 1);
        mEnabled = a.getBoolean(R.styleable.BoundedLinearLayout_enabled, true);
        mAspectWdivH = a.getFloat(R.styleable.BoundedLinearLayout_aspectWdivH, 0);
        mIncludeLayoutResId = a.getResourceId(R.styleable.BoundedLinearLayout_include, -1);
        a.recycle();

        // LayoutInflater inflater = SysUtils.getServiceSafe(context, Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = LayoutInflater.from(context);
        if (mIncludeLayoutResId > 0) {
            View view = inflater.inflate(mIncludeLayoutResId, this);
            this.addView(view);
        }
    }

    /**
     * Adjust width and/or height first by percent then by pixels.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mEnabled) {
            int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
            int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

            // Adjust width to max of percent of parent.
            int percentWidth = Math.round(measuredWidth * mBoundedWidthPercent);
            if (measuredWidth > percentWidth) {
                measuredWidth = percentWidth;
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.AT_MOST);
            }
            // Adjust height to max of percent of parent.
            int percentHeight = Math.round(measuredHeight * mBoundedHeightPercent);
            if (measuredHeight > percentHeight) {
                measuredHeight = percentHeight;
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST);
            }

            // Adjust width as necessary
            if (measuredWidth > mBoundedWidthPixel) {
                // int measureMode = MeasureSpec.getMode(widthMeasureSpec);
                measuredWidth  = mBoundedWidthPixel;
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.AT_MOST);
            }
            // Adjust height as necessary
            if (measuredHeight > mBoundedHeightPixel) {
                // int measureMode = MeasureSpec.getMode(heightMeasureSpec);
                measuredHeight = mBoundedHeightPixel;
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.AT_MOST);
            }

            if (mAspectWdivH != 0) {
                if (mAspectWdivH > 0) {
                    int newWidth = Math.round(mAspectWdivH * measuredHeight);
                    if (newWidth <= measuredWidth) {
                        measuredWidth = newWidth;
                        widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                    } else {
                        measuredHeight =  Math.min(measuredHeight, Math.round(measuredWidth / mAspectWdivH));
                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
                    }
                } else {
                    int newHeight = Math.round(measuredWidth / -mAspectWdivH);

                    if (newHeight <= measuredHeight) {
                        measuredHeight = newHeight;
                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(measuredHeight, MeasureSpec.EXACTLY);
                    } else {
                        measuredWidth = Math.min(measuredWidth,  Math.round(-mAspectWdivH * measuredHeight));
                        widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
                    }
                }
            }

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}