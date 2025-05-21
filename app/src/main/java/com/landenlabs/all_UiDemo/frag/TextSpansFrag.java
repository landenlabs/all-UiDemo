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

package com.landenlabs.all_UiDemo.frag;

import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate Text wrap and Spannable techniques
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://LanDenLabs.com/android"> author's web-site </a>
 */

public class TextSpansFrag extends UiFragment {

    private View mRootView;
    private ViewGroup mHolder;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_text_spans, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.layout_text_span_id;
    }

    @Override
    public String getName() {
        return "TextSpans";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        mHolder = Ui.viewById(mRootView, R.id.text_span_holder);
        LayoutInflater inflater = LayoutInflater.from(requireContext());

        String lines = "5 Lines\nHello World2\nHello World3\nHello World4\nHello World5";
        int row1 = lines.indexOf("\n");
        int row2 = lines.indexOf("\n", row1+1);
        int row3 = lines.indexOf("\n", row2+1);
        int row4 = lines.indexOf("\n", row3+1);
        int row5 = lines.length();
        TextView tv;
        SpannableString span;


        tv = (TextView)inflater.inflate(R.layout.text_span_item, mHolder, false);
        tv.setText(lines);
        mHolder.addView(tv);

        tv = (TextView)inflater.inflate(R.layout.text_span_item, mHolder, false);
        span = new SpannableString(lines);
        span.setSpan(new StyleSpan(Typeface.BOLD), row1, row2, 0);
        span.setSpan(new ForegroundColorSpan(Color.RED), row2, row3, 0);
        span.setSpan(new BackgroundColorSpan(Color.GREEN), row3, row4, 0);
        // span.setSpan(new  RelativeSizeSpan(1.2f);
        tv.setText(span);
        mHolder.addView(tv);

        tv = (TextView)inflater.inflate(R.layout.text_span_item, mHolder, false);
        span = new SpannableString(lines);
        span.setSpan(new RelativeSizeSpan(1.2f), row1, row2, 0);
        span.setSpan(new RelativeSizeSpan(1.0f), row2, row3, 0);
        span.setSpan(new RelativeSizeSpan(0.8f), row3, row4, 0);
        span.setSpan(new RelativeSizeSpan(0.5f), row4, row5, 0);
        tv.setText(span);
        mHolder.addView(tv);

        /*
            <TextView
                android:layout_width="0dp"
                android:layout_height="70dp"
                android:layout_marginEnd="4dp"
                android:layout_weight="1"
                android:breakStrategy="high_quality"     <----------
                android:gravity="left|center_vertical"
                android:lines="2"
                android:text="@string/subscription_daily_promo_and"
                android:textAppearance="@style/textStrong14" />

         */

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)tv.getLayoutParams();
        lp.setMargins(0,0,0,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            for (BlendMode mode : BlendMode.values()) {
                //                     012345678901234567890
                final String blend1 = "Red Green Blue Yellow " + mode.name();
                SpannableString ss = new SpannableString(blend1);
                ss.setSpan(new ForegroundColorSpan(Color.RED), 0, 4, 0);
                ss.setSpan(new ForegroundColorSpan(Color.GREEN), 4, 10, 0);
                ss.setSpan(new ForegroundColorSpan(Color.BLUE), 10, 15, 0);
                ss.setSpan(new ForegroundColorSpan(Color.YELLOW), 15, 21, 0);

                tv = (TextView) inflater.inflate(R.layout.text_span_item, mHolder, false);
                tv.getPaint().setBlendMode(mode);
                // tv.setForegroundTintBlendMode(mode);
                tv.setText(ss);
                mHolder.addView(tv, lp);

                tv = (TextView) inflater.inflate(R.layout.text_span_item, mHolder, false);
                tv.setText("Yellow " + mode.name());
                tv.setTextColor(getBlendColor(Color.WHITE, Color.YELLOW, mode));
                mHolder.addView(tv, lp);

                tv = (TextView) inflater.inflate(R.layout.text_span_item, mHolder, false);
                tv.setText(ss);
                mHolder.addView(tv, lp);
            }
        }

        for (double fraction = 0.1; fraction < 1.0; fraction += 0.1) {
            final String blend1 = String.format("Red Green Blue Yellow %.2f lighten", fraction);
            SpannableString ss = new SpannableString(blend1);
            ss.setSpan(new ForegroundColorSpan(lighten(Color.RED, fraction)), 0, 4, 0);
            ss.setSpan(new ForegroundColorSpan(lighten(Color.GREEN, fraction)), 4, 10, 0);
            ss.setSpan(new ForegroundColorSpan(lighten(Color.BLUE, fraction)), 10, 15, 0);
            ss.setSpan(new ForegroundColorSpan(lighten(Color.YELLOW, fraction)), 15, 21, 0);

            tv = (TextView) inflater.inflate(R.layout.text_span_item, mHolder, false);
            tv.setText(ss);

            mHolder.addView(tv, lp);
        }

        for (double fraction = 0.1; fraction < 1.0; fraction += 0.1) {
            final String blend1 = String.format("Red Green Blue Yellow %.2f darken", fraction);
            SpannableString ss = new SpannableString(blend1);
            ss.setSpan(new ForegroundColorSpan(darken(Color.RED, fraction)), 0, 4, 0);
            ss.setSpan(new ForegroundColorSpan(darken(Color.GREEN, fraction)), 4, 10, 0);
            ss.setSpan(new ForegroundColorSpan(darken(Color.BLUE, fraction)), 10, 15, 0);
            ss.setSpan(new ForegroundColorSpan(darken(Color.YELLOW, fraction)), 15, 21, 0);

            tv = (TextView) inflater.inflate(R.layout.text_span_item, mHolder, false);
            tv.setText(ss);

            mHolder.addView(tv, lp);
        }
    }

    @RequiresApi(29)
    @ColorInt
    private int getBlendColor(@ColorInt int bgColor, @ColorInt int fgColor, BlendMode mode) {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); //make a 1-pixel Bitmap
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(bgColor); //color we want to apply filter to
        canvas.drawColor(fgColor, mode); //apply filter
        return bitmap.getPixel(0,  0);
    }

    @ColorInt
    public static int lighten(@ColorInt int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightenColor(red, fraction);
        green = lightenColor(green, fraction);
        blue = lightenColor(blue, fraction);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    @ColorInt
    public static int darken(@ColorInt int color, double fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, fraction);
        green = darkenColor(green, fraction);
        blue = darkenColor(blue, fraction);
        int alpha = Color.alpha(color);

        return Color.argb(alpha, red, green, blue);
    }

    private static int darkenColor(int color, double fraction) {
        return (int) Math.max(Math.min(color - (color * fraction), color - (255*fraction)), 0);
    }

    private static int lightenColor(int color, double fraction) {
        return (int) Math.min(Math.max(color + (color * fraction), color + (255*fraction)), 255);
    }
}
