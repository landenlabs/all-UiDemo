package com.landenlabs.all_UiDemo.Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Debug version of ImageView to log scaling of images.
 *
 * Created by Dennis Lang on 7/4/16.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */
public class ExImageView extends ImageView {

    private int mImageResID;
    private float[] values = new float[9];

    public ExImageView(Context context) {
        super(context);
    }

    public ExImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setImageResource(int resId) {
        mImageResID = resId;
        super.setImageResource(resId);
    }

    public int getResourceId() {
        return mImageResID;
    }


    /**
     * Adjust width and/or height first by percent then by pixels.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.d("foo", String.format("Res:%d W:%d H:%d Measured w:%d H:%d",
                mImageResID, getWidth(), getHeight(), measuredWidth, measuredHeight));
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long startMillis = System.currentTimeMillis();
        try {
            super.onDraw(canvas);
        } catch (Exception ex) {
            Log.e("foo", ex.getLocalizedMessage() + "\n" + getContentDescription() + " id=" + mImageResID, ex);
            return;
        }
        long endMillis = System.currentTimeMillis();

        Matrix matrix = getImageMatrix();
        if (!matrix.isIdentity()) {

            matrix.getValues(values);
            float scaleX = values[Matrix.MSCALE_X];
            float scaleY = values[Matrix.MSCALE_Y];

            Log.d("foo", String.format("Res:%d W:%d H:%d Scale X:%.2f Y:%.2f",
                    mImageResID, getWidth(), getHeight(), scaleX, scaleY));
        }
    }
}
