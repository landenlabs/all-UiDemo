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

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.NonNull;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * A Drawable that changes the size of another Drawable based on its current
 * level value.  You can control how much the child Drawable changes in width
 * and height based on the level, as well as a gravity to control where it is
 * placed in its overall container.  Most often used to implement things like
 * progress bars.
 *
 * <p>It can be defined in an XML file with the <scale> element.

 * <p>
 * attr ref android.R.styleable#ScaleImage_scaleWidth
 * attr ref android.R.styleable#ScaleImage_scaleHeight
 * attr ref android.R.styleable#ScaleImage_scaleGravity
 * attr ref android.R.styleable#ScaleImage_drawable
 */
class ScaleImage extends Drawable implements Drawable.Callback {
    private final ScaleState mScaleState;
    private boolean mMutated;
    private final Rect mTmpRect = new Rect();

    ScaleImage() {
        this(null, null);
    }

    public ScaleImage(Drawable drawable, int gravity, float scaleWidth, float scaleHeight) {
        this(null, null);

        mScaleState.mDrawable = drawable;
        mScaleState.mGravity = gravity;
        mScaleState.mScaleWidth = scaleWidth;
        mScaleState.mScaleHeight = scaleHeight;

        if (drawable != null) {
            drawable.setCallback(this);
        }
    }

    /**
     * Returns the drawable scaled by this ScaleImage.
     */
    public Drawable getDrawable() {
        return mScaleState.mDrawable;
    }

    private static float getPercent(TypedArray a, int name) {
        String s = a.getString(name);
        if (s != null) {
            if (s.endsWith("%")) {
                String f = s.substring(0, s.length() - 1);
                return Float.parseFloat(f) / 100.0f;
            }
        }
        return -1;
    }

    @Override
    public void inflate(@NonNull Resources r, @NonNull XmlPullParser parser, @NonNull AttributeSet attrs)
            throws XmlPullParserException, IOException {
        super.inflate(r, parser, attrs);

        int type;

        /*
        TypedArray a = r.obtainAttributes(attrs, com.android.internal.R.styleable.ScaleDrawable);

        float sw = getPercent(a, com.android.internal.R.styleable.ScaleDrawable_scaleWidth);
        float sh = getPercent(a, com.android.internal.R.styleable.ScaleDrawable_scaleHeight);
        int g = a.getInt(com.android.internal.R.styleable.ScaleDrawable_scaleGravity, Gravity.LEFT);
        Drawable dr = a.getDrawable(com.android.internal.R.styleable.ScaleDrawable_drawable);

        a.recycle();


        final int outerDepth = parser.getDepth();
        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                && (type != XmlPullParser.END_TAG || parser.getDepth() > outerDepth)) {
            if (type != XmlPullParser.START_TAG) {
                continue;
            }
            dr = Drawable.createFromXmlInner(r, parser, attrs);
        }

        if (dr == null) {
            throw new IllegalArgumentException("No drawable specified for <scale>");
        }

        mScaleState.mDrawable = dr;
        mScaleState.mScaleWidth = sw;
        mScaleState.mScaleHeight = sh;
        mScaleState.mGravity = g;
        if (dr != null) {
            dr.setCallback(this);
        }

        */
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable who) {
    }

    @Override
    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {

    }

    @Override
    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {

    }

    /*
    // overrides from Drawable.Callback
    public void invalidateDrawable(Drawable who) {
        if (mCallback != null) {
            mCallback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        if (mCallback != null) {
            mCallback.scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(Drawable who, Runnable what) {
        if (mCallback != null) {
            mCallback.unscheduleDrawable(this, what);
        }
    }
    */


    // overrides from Drawable

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mScaleState.mDrawable.getLevel() != 0)
            mScaleState.mDrawable.draw(canvas);
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations()
                | mScaleState.mChangingConfigurations
                | mScaleState.mDrawable.getChangingConfigurations();
    }

    @Override
    public boolean getPadding(@NonNull Rect padding) {
        // XXX need to adjust padding!
        return mScaleState.mDrawable.getPadding(padding);
    }

    @Override
    public boolean setVisible(boolean visible, boolean restart) {
        mScaleState.mDrawable.setVisible(visible, restart);
        return super.setVisible(visible, restart);
    }

    @Override
    public void setAlpha(int alpha) {
        mScaleState.mDrawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mScaleState.mDrawable.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return mScaleState.mDrawable.getOpacity();
    }

    @Override
    public boolean isStateful() {
        return mScaleState.mDrawable.isStateful();
    }

    @Override
    protected boolean onStateChange(@NonNull int[] state) {
        boolean changed = mScaleState.mDrawable.setState(state);
        onBoundsChange(getBounds());
        return changed;
    }

    @Override
    protected boolean onLevelChange(int level) {
        mScaleState.mDrawable.setLevel(level);
        onBoundsChange(getBounds());
        invalidateSelf();
        return true;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        final Rect r = mTmpRect;
        int level = getLevel();
        int w = bounds.width();
        final int iw =  mScaleState.mDrawable.getIntrinsicWidth();
        if (mScaleState.mScaleWidth > 0) {
            w -= (int) ((w - iw) * (10000 - level) * mScaleState.mScaleWidth / 10000);
        }
        int h = bounds.height();
        final int ih =  mScaleState.mDrawable.getIntrinsicHeight();
        if (mScaleState.mScaleHeight > 0) {
            h -= (int) ((h - ih) * (10000 - level) * mScaleState.mScaleHeight / 10000);
        }
        Gravity.apply(mScaleState.mGravity, w, h, bounds, r);

        if (w > 0 && h > 0) {
            mScaleState.mDrawable.setBounds(r.left, r.top, r.right, r.bottom);
        }
    }

    @Override
    public int getIntrinsicWidth() {
        return mScaleState.mDrawable.getIntrinsicWidth();
    }

    @Override
    public int getIntrinsicHeight() {
        return mScaleState.mDrawable.getIntrinsicHeight();
    }

    @Override
    public ConstantState getConstantState() {
        if (mScaleState.canConstantState()) {
            mScaleState.mChangingConfigurations = super.getChangingConfigurations();
            return mScaleState;
        }
        return null;
    }

    @NonNull
    @Override
    public Drawable mutate() {
        if (!mMutated && super.mutate() == this) {
            mScaleState.mDrawable.mutate();
            mMutated = true;
        }
        return this;
    }

    final static class ScaleState extends ConstantState {
        Drawable mDrawable;
        int mChangingConfigurations;
        float mScaleWidth;
        float mScaleHeight;
        int mGravity;

        private boolean mCheckedConstantState;
        private boolean mCanConstantState;

        ScaleState(ScaleState orig, ScaleImage owner, Resources res) {
            if (orig != null) {
                Drawable.ConstantState constantState = orig.mDrawable.getConstantState();
                if (constantState != null) {
                    if (res != null) {
                        mDrawable = constantState.newDrawable(res);
                    } else {
                        mDrawable = constantState.newDrawable();
                    }
                }
                mDrawable.setCallback(owner);
                mScaleWidth = orig.mScaleWidth;
                mScaleHeight = orig.mScaleHeight;
                mGravity = orig.mGravity;
                mCheckedConstantState = mCanConstantState = true;
            }
        }

        @NonNull
        @Override
        public Drawable newDrawable() {
            return new ScaleImage(this, null);
        }

        @NonNull
        @Override
        public Drawable newDrawable(Resources res) {
            return new ScaleImage(this, res);
        }

        @Override
        public int getChangingConfigurations() {
            return mChangingConfigurations;
        }

        boolean canConstantState() {
            if (!mCheckedConstantState) {
                mCanConstantState = mDrawable.getConstantState() != null;
                mCheckedConstantState = true;
            }

            return mCanConstantState;
        }
    }

    private ScaleImage(ScaleState state, Resources res) {
        mScaleState = new ScaleState(state, this, res);
    }
}