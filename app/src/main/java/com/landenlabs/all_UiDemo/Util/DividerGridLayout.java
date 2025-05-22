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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * https://gist.github.com/Gaelan-Bolger/a5622eb3da9896254335404aa9370785
 * <p>
 * <com.landenlabs.all_UiDemo.Util.DividerGridLayout
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:alignmentMode="alignBounds"
 *     app:columnCount="2">
 * <p>
 *  </com.landenlabs.all_UiDemo.Util.DividerGridLayout>
 */
public class DividerGridLayout extends GridLayout {

    private Paint vPaint;
    private Paint hPaint;

    public DividerGridLayout(Context context) {
        this(context, null);
    }

    public DividerGridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DividerGridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        /*
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            int left = view.getLeft();
            int top = view.getTop();
            int bottom = view.getBottom();
            int right = view.getRight();
            // if (i % 2 == 0) {
                canvas.drawLine(right - 1, top, right - 1, bottom, vPaint);
            // } else {
                canvas.drawLine(left, top, left, bottom, vPaint);
            // }
            // if (i >= getColumnCount())
                canvas.drawLine(left, top, right, top, hPaint);
            canvas.drawLine(left, bottom-1, right, bottom-1, hPaint);
        }
         */
        drawDividers(canvas);
        super.dispatchDraw(canvas);
    }


    private void drawLine(Canvas graphics, int x1, int y1, int x2, int y2, Paint paint) {
        // if (isLayoutRtl()) {
            int width = getWidth();
            graphics.drawLine(width - x1, y1, width - x2, y2, paint);
        // } else {
        //     graphics.drawLine(x1, y1, x2, y2, paint);
        // }
    }

    protected void drawDividers(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.argb(50, 255, 0, 0));
        paint.setStrokeWidth(6.0f);

        int top    =               getPaddingTop()   ;
        int left   =               getPaddingLeft()  ;
        int right  = getWidth()  - getPaddingRight() ;
        int bottom = getHeight() - getPaddingBottom();

        try {
            // public int[] getLocations() {
            // horizontalAxis.get(this);
            int[] xs = (int[])axisGetLocations.invoke(horizontalAxis.get(this));

            // int[] xs = mHorizontalAxis.locations;
            if (xs != null) {
                for (int j : xs) {
                    int x = left + j;
                    drawLine(canvas, x, top, x, bottom, paint);
                }
            }

            int[] ys = (int[])axisGetLocations.invoke(verticalAxis.get(this));
            // int[] ys = mVerticalAxis.locations;
            if (ys != null) {
                for (int value : ys) {
                    int y = top + value;
                    drawLine(canvas, left, y, right, y, paint);
                }
            }

        } catch (Exception ex) {
            Log.e("den", ex.getMessage());
        }
    }


    Class<?> Axis;
    Field horizontalAxis;
    Field verticalAxis;
    Method axisGetLocations;

    private void init()  {
        vPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //    vPaint.setColor(Color.parseColor("#22000000"));
        vPaint.setColor(Color.GREEN);
        vPaint.setStyle(Paint.Style.STROKE);
        vPaint.setStrokeWidth(6.0f);
        hPaint = new Paint(vPaint);
        hPaint.setStrokeWidth(6.0f);

        try {
            horizontalAxis = GridLayout.class.getDeclaredField("mHorizontalAxis");
            horizontalAxis.setAccessible(true);
            verticalAxis = GridLayout.class.getDeclaredField("mVerticalAxis");
            verticalAxis.setAccessible(true);
            Axis = Class.forName("android.widget.GridLayout$Axis");
            axisGetLocations = Axis.getMethod("getLocations");
            axisGetLocations.setAccessible(true);

        } catch (Exception ex) {
            Log.e("den", ex.getMessage());
        }
    }
}
