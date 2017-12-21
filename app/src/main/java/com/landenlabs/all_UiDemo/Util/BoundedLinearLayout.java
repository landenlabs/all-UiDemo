/**
 * Copyright (c) 2017 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @author Dennis Lang  (1/10/2017)
 * @see http://landenlabs.com
 */


package com.landenlabs.all_UiDemo.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.landenlabs.all_UiDemo.R;


/**
 * Implementation of a bounded LinearLayout adds four new parameters
 * to limit the maximum bounds.
 *
 * @attr ref R.styleable#BoundedView_bounded_widthPercent
 * @attr ref R.styleable#BoundedView_bounded_heightPercent
 *
 * @attr ref R.styleable#BoundedView_bounded_width
 * @attr ref R.styleable#BoundedView_bounded_height
 */

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
 *          app:bounded_widthPercent="0.8"
 *          app:bounded_width="200dp">
 *          &lt;TextView
 *              android:layout_width="wrap_content"
 *              android:layout_height="wrap_content" />
 *      &lt;/com.wsi.android.weather.ui.widget.BoundedLinearLayout>
 * </pre>
 */
public class BoundedLinearLayout extends LinearLayout {

    private final int mBoundedWidthPixel;
    private final int mBoundedHeightPixel;
    private final float mBoundedWidthPercent;
    private final float mBoundedHeightPercent;

    public BoundedLinearLayout(Context context) {
        super(context);
        mBoundedWidthPixel = 0;
        mBoundedHeightPixel = 0;
        mBoundedWidthPercent = 0;
        mBoundedHeightPercent = 0;
    }

    public BoundedLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedView, defStyle, 0);
        mBoundedWidthPixel = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_width, 0);
        mBoundedHeightPixel = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_height, 0);
        mBoundedWidthPercent = a.getFloat(R.styleable.BoundedView_bounded_widthPercent, 0);
        mBoundedHeightPercent = a.getFloat(R.styleable.BoundedView_bounded_heightPercent, 0);
        a.recycle();
    }

    public BoundedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedView, 0, 0);
        mBoundedWidthPixel = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_width, 0);
        mBoundedHeightPixel = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_height, 0);
        mBoundedWidthPercent = a.getFloat(R.styleable.BoundedView_bounded_widthPercent, 0);
        mBoundedHeightPercent = a.getFloat(R.styleable.BoundedView_bounded_heightPercent, 0);
        a.recycle();
    }


    /**
     * Adjust width and/or height first by percent then by pixels.
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);

        // Adjust width to max of percent of parent.
        int percentWidth = Math.round(measuredWidth * mBoundedWidthPercent);
        if (mBoundedWidthPercent > 0 && getMeasuredWidth() > percentWidth) {
            int measureMode = MeasureSpec.AT_MOST;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(percentWidth, measureMode);
        }
        // Adjust height to max of percent of parent.
        int percentHeight = Math.round(measuredHeight * mBoundedHeightPercent);
        if (mBoundedHeightPercent > 0 && getMeasuredHeight() > percentHeight) {
            int measureMode = MeasureSpec.AT_MOST;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(percentHeight, measureMode);
        }

        // Adjust width as necessary
        if (mBoundedWidthPixel > 0 && mBoundedWidthPixel < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            measureMode = MeasureSpec.AT_MOST;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mBoundedWidthPixel, measureMode);
        }
        // Adjust height as necessary
        if (mBoundedHeightPixel > 0 && mBoundedHeightPixel < measuredHeight) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            measureMode = MeasureSpec.AT_MOST;
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mBoundedHeightPixel, measureMode);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}