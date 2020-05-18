package com.landenlabs.all_UiDemo.Seek;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;

/**
 * Custom seekbar with text bubble over thumbnail position.
 */
public class MySeekBar1   extends AppCompatSeekBar {
    private final Paint paint;
    private final Rect bounds = new Rect();

    public MySeekBar1 (Context context) {
        super(context);
        paint = new Paint();
    }

    public MySeekBar1 (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
    }

    public MySeekBar1 (Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        float thumb_x = (float)this.getProgress() * this.getWidth() /this.getMax();
        float thumb_y = this.getHeight();

        paint.setColor(Color.BLACK);
        paint.setTextSize(80);
        String text = String.valueOf(this.getProgress());
        paint.getTextBounds(text, 0, text.length(), bounds);
        c.drawText(text, thumb_x - bounds.width()/2f , thumb_y - bounds.height(),  paint);
    }
}