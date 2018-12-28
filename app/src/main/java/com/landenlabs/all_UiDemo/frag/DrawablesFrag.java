package com.landenlabs.all_UiDemo.frag;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate GridLayout, Linear, Scroll, Percent layout of stuff.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

public class DrawablesFrag  extends UiFragment  {

    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.drawables_frag, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.drawables_id;
    }

    @Override
    public String getName() {
        return "Drawables";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        @ColorInt int tintColor = Color.GREEN;
        PorterDuffColorFilter colorFilter = new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN);

        // drawable.setTint(tintColor);
        // drawable.setColorFilter(tintColor, PorterDuff.Mode.MULTIPLY);
        // drawable = drawable.mutate();

        // view1.setColorFilter(colorFilter);
        // This works because ImageView draws a colored image on top of src image and blends them.
        // Read https://blog.danlew.net/2014/08/18/fast-android-asset-theming-with-colorfilter/



        // Bitmap Drawable - tint works
        Drawable drawable2 = ContextCompat.getDrawable(getContext(), R.drawable.shadow7);
        if (Build.VERSION.SDK_INT >= 21) {
            drawable2.setTint(tintColor);
        }
        // drawable2.setColorFilter(colorFilter);
        ImageView view2 = Ui.viewById(mRootView, R.id.drawables_2);
        view2.setImageDrawable(drawable2);



        // Vector Drawable - tint does not work.
        if (true) {
            Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.scr_hourly);
            // drawable3.setColorFilter(colorFilter);  // Does not work.
            if (Build.VERSION.SDK_INT >= 21) {
                drawable3.setTint(tintColor);
            }
            ImageView view3 = Ui.viewById(mRootView, R.id.drawables_3);
            view3.setImageDrawable(drawable3);
            // view3.setBackgroundDrawable(drawable3);
        } else {
            Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.scr_hourly);
            DrawableCompat.setTint(drawable3, tintColor);
            ImageView view3 = Ui.viewById(mRootView, R.id.drawables_3);
            view3.setImageDrawable(drawable3);
        }

    }
}
