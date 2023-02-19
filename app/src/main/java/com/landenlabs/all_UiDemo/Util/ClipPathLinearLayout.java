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
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 *  LinearLayout which can clip its drawing area.
 *  https://medium.com/appkode/clipping-in-android-quickly-qualitatively-cheap-3ccfd31d5d6b
 */
public class ClipPathLinearLayout extends LinearLayout {
    private final Path path1 = new Path();
    private final Path path2 = new Path();
    private int radius1 = 0;
    private int radius2 = 0;

    // ---------------------------------------------------------------------------------------------

    public ClipPathLinearLayout(Context context) {
        super(context);
        init();
    }

    public ClipPathLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipPathLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ClipPathLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
    }

    private void initPath() {
        if (path1.isEmpty()) {
            if (radius1 == 0) radius1 = getHeight();
            if (radius2 == 0) radius2 = getHeight()/2;
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
        // Clip everything  background, children and foreground
        canvas.clipPath(path1);
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        initPath();
        // Clip children
        canvas.clipPath(path2);
        super.dispatchDraw(canvas) ;
    }

}