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
 * @see http://LanDenLabs.com/
 */

package com.landenlabs.all_UiDemo.Util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.landenlabs.all_UiDemo.R;

/**
 * https://gist.githubusercontent.com/cblunt/3175620/raw/1571e9e36f87f5455ecf95e34194c37b77bbc73e/MaskedImageView.java
 */

@SuppressWarnings("ConstantConditions")
public class BlendImageView extends AppCompatImageView {

    private Bitmap mutableBitmap;
    private Canvas canvas;
    private PorterDuffXfermode mode;
    private PorterDuffXfermode bgMode;
    private Paint paintBlend;
    private Paint paintNormal;
    private Drawable backgroundDrawable;
    private Drawable foregroundDrawable;
    private PorterDuff.Mode mBlendMode = PorterDuff.Mode.LIGHTEN;

    public BlendImageView(Context context) {
        super(context);
    }

    public BlendImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BlendImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.BlendImageView, 0, 0);
        try {
            int resId;
            resId = a.getResourceId(R.styleable.BlendImageView_foreground, -1);
            if (resId != -1)
            foregroundDrawable = getContext().getResources().getDrawable(resId, getContext().getTheme());

            resId = a.getResourceId(R.styleable.BlendImageView_background, -1);
            if (resId != -1)
            backgroundDrawable  = getContext().getResources().getDrawable(resId, getContext().getTheme());

            String modeStr = a.getString(R.styleable.BlendImageView_mode);
            mBlendMode = PorterDuff.Mode.valueOf(modeStr);
        } catch (Exception ignore) {

        }
        a.recycle();


        mode = new PorterDuffXfermode(mBlendMode);
        paintBlend = new Paint();
        paintNormal = new Paint(); // (Paint.ANTI_ALIAS_FLAG);
        bgMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas onDrawCanvas) {
        if (mutableBitmap == null) {
            // Need to wait until measure is ready.
            mutableBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(mutableBitmap);
        }

        paintBlend.setFilterBitmap(false);
        paintBlend.setXfermode(mode);

        paintNormal.setFilterBitmap(false);
        // paintNormal.setXfermode(mode);

        if (false) {
            if (getDrawable() != null) {
                Bitmap srcBm = drawableToBitmap(getDrawable(), getMeasuredWidth(), getMeasuredHeight());
                canvas.drawBitmap(srcBm, 0, 0, paintBlend);
            }
        }

        if (false && getBackground() != null) {
            Drawable background = getBackground();
            // background = backgroundDrawable;

            if (false) {
                if (background instanceof BitmapDrawable) {
                    ((BitmapDrawable) background).getPaint().setXfermode(bgMode);
                } else if (background instanceof NinePatchDrawable) {
                    ((NinePatchDrawable) background).getPaint().setXfermode(bgMode);
                } else if (background instanceof ShapeDrawable) {
                    ((ShapeDrawable) background).getPaint().setXfermode(bgMode);
                } else if (background instanceof PaintDrawable) {
                    ((PaintDrawable) background).getPaint().setXfermode(bgMode);
                } else if (background instanceof VectorDrawable) {
                    // ((VectorDrawable)background).getPaint().setXfermode(bgMode);
                }
                //    canvas.drawPaint(paintBlend);
                background.draw(canvas);

            } else {
                // canvas.drawPaint(paintBlend);
                Bitmap bgBm = drawableToBitmap(background, getMeasuredWidth(), getMeasuredHeight());
                canvas.drawBitmap(bgBm, 0, 0, paintNormal);
            }
        }

        if (false) {
            canvas.drawPaint(paintNormal);
            Bitmap srcBm = drawableToBitmap(getDrawable(), getMeasuredWidth(), getMeasuredHeight());
            canvas.drawBitmap(srcBm, 0, 0, paintBlend);
            // canvas.drawPaint(paintBlend);
            // getDrawable().draw(canvas);
        }

        if (false) {
            if (getDrawable() != null) {
                getDrawable().setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());
                canvas.drawPaint(paintBlend);
                final int scrollX = getScrollX();
                final int scrollY = getScrollY();

                if ((scrollX | scrollY) == 0) {
                    getDrawable().draw(canvas);
                } else {
                    canvas.translate(scrollX, scrollY);
                    getDrawable().draw(canvas);
                    canvas.translate(-scrollX, -scrollY);
                }
            }
        }

        if (backgroundDrawable != null) {
            draw(backgroundDrawable, canvas);
        }

        if (foregroundDrawable != null) {
            Bitmap bitmap = null;

            if (foregroundDrawable instanceof BitmapDrawable) {
                ((BitmapDrawable) foregroundDrawable).getPaint().setXfermode(mode);
            } else if (foregroundDrawable instanceof NinePatchDrawable) {
                ((NinePatchDrawable) foregroundDrawable).getPaint().setXfermode(mode);
            } else if (foregroundDrawable instanceof ShapeDrawable) {
                ((ShapeDrawable) foregroundDrawable).getPaint().setXfermode(mode);
            } else if (foregroundDrawable instanceof PaintDrawable) {
                ((PaintDrawable) foregroundDrawable).getPaint().setXfermode(mode);
            } else {
                bitmap = drawableToBitmap(foregroundDrawable, getMeasuredWidth(), getMeasuredHeight());
            }

            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, paintBlend);
            } else {
                draw(foregroundDrawable, canvas);
            }
        }

        onDrawCanvas.drawBitmap(mutableBitmap, 0, 0, paintNormal);
    }

    private void draw(Drawable drawable, Canvas canvas) {
        drawable.setBounds(0, 0, getRight() - getLeft(), getBottom() - getTop());


        final int scrollX = getScrollX();
        final int scrollY = getScrollY();

        if ((scrollX | scrollY) == 0) {
            drawable.draw(canvas);
        } else {
            canvas.translate(scrollX, scrollY);
            drawable.draw(canvas);
            canvas.translate(-scrollX, -scrollY);
        }
    }

    public Drawable getForegroundCompat() {
        if (Build.VERSION.SDK_INT >= 23) {
            return getForeground();
        } else {
            return foregroundDrawable;
        }
    }

    public void setForegroundCompat(int resId) {
        setForegroundCompat(getContext().getResources().getDrawable(resId));
    }

    public void setForegroundCompat(Drawable d) {
        d.setCallback(this);
        d.setVisible(getVisibility() == VISIBLE, false);

        foregroundDrawable = d;

        requestLayout();
        invalidate();
    }

    /*
    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        srcBitmap = bitmap;

        invalidate();
    }
    */

    // drawable.getIntrinsicWidth(
    private static Bitmap drawableToBitmap (Drawable drawable, int width, int height) {
        Bitmap bitmap;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (width <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    private  BitmapDrawable drawableToBitmapDrawable(
            Drawable drawable, int width, int height, PorterDuffXfermode mode) {
        Bitmap bm = drawableToBitmap(drawable, width, height);
        BitmapDrawable bmd = new BitmapDrawable(getResources(), bm);
        bmd.getPaint().setXfermode(mode);
        return bmd;
    }
}