/*
 * Copyright (c) 2021 Dennis Lang (LanDen Labs) landenlabs@gmail.com
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
 * @see http://LanDenLabs.com/
 */

package com.landenlabs.all_UiDemo.Util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *  LinearLayout which can clip its drawing area and optionally clear this objects background
 *  using the PorterDuff filter.
 *
 *  https://medium.com/appkode/clipping-in-android-quickly-qualitatively-cheap-3ccfd31d5d6b
 *
 */
public class ClipPathPorterLinearLayout extends LinearLayout {
    private Path path1 = new Path();
    private Path path2 = new Path();
    private int radius1 = 0;
    private int radius2 = 0;
    private Paint paint;
    private final Rect rect = new Rect();
    private final RectF rectF = new RectF();

    private Map<View, Paint> childPaints = new HashMap<>();

    // ---------------------------------------------------------------------------------------------

    public ClipPathPorterLinearLayout(Context context) {
        super(context);
        init();
    }

    public ClipPathPorterLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipPathPorterLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ClipPathPorterLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setXfermode( new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    private void initPath() {
        if (path1.isEmpty()) {
            // if (radius1 == 0) radius1 = getHeight();
            // if (radius2 == 0) radius2 = getHeight()/2;
            path1.addCircle(getWidth() / 2f, getHeight() / 2f,radius1, Path.Direction.CW);
            path2.addCircle(getWidth() / 2f, getHeight() / 2f, radius2, Path.Direction.CW);
        }
    }

    public void setRadius(int radius1, int radius2) {
        this.radius1 = radius1;
        this.radius2 = radius2;
        path1.reset();
        path2.reset();
        initPath();
        invalidate();
    }
    
    @Override
    public void draw(Canvas canvas) {
        initPath();
        super.draw(canvas);
        // Paint over everything  background, children and foreground
        canvas.drawPath(path1, paint);
    }

    /**
     * Optionally clean this objects container background if child has tag set to "clear"
     */
    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (child.getTag() instanceof String) {
            String mode = (String)child.getTag();
            Paint paint = childPaints.get(child);
            if (paint == null) {
                paint = new Paint();
                paint.setColor(Color.WHITE);
                childPaints.put(child, paint);

                child.getDrawingRect(rect);
                rectF.set(rect);
                rectF.offset(child.getLeft() + child.getTranslationX(),child.getTop() + child.getTranslationY());

                switch (mode) {
                    case "clear":

                        // Other filters to research:
                        //    paint.setColorFilter()
                        //    child.setLayerPaint(paint);
                        //    canvas.setDrawFilter();

                        // Example of clipping children, this example clips entire child making
                        // it appear invisible. Rectangle can be adjusted as needed.
                        if (false) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                canvas.clipOutRect(rect);
                            } else {
                                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                            }
                        }

                        // Example clipping parent's background using PorterDuff. This example only
                        // implements CLEAR filter.
                        paint.setXfermode( new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                        canvas.drawRect(rectF, paint);
                        break;
                    case "xor":
                        paint.setXfermode( new PorterDuffXfermode(PorterDuff.Mode.XOR));
                        child.setLayerPaint(paint);
                        break;
                }
            }
        }
        return super.drawChild(canvas, child, drawingTime);
    }
}