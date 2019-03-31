package com.landenlabs.all_UiDemo.frag;

/*
 * Copyright (c) 2019 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (3/21/2015)
 *  @see http://landenlabs.com
 *
 */

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.landenlabs.all_UiDemo.ALog.ALog;
import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 *  Animate vector checkmarks
 *
 *  @author Dennis Lang  (3/21/2015)
 */

public class AnimatedVectorDrawableFrag
        extends UiFragment
        implements View.OnClickListener{

        private View mRootView;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.animated_vector_drawable_frag, container, false);

            setup();
            return mRootView;
        }

        @Override
        public int getFragId() {
            return R.id.anim_vector_drawable_id;
        }

        @Override
        public String getName() {
            return "AnimVecDraw";
        }

        @Override
        public String getDescription() {
            return "??";
        }

    @Override
    public void onClick(View view) {
        if (view instanceof CompoundButton) {
            // The CompoundButton objects, such as CheckBox and Toggle will auto toggle their state
            ALog.d.tagMsg(this, "a checked=", ((CompoundButton) view).isChecked(), " selected=",
                    view.isSelected());
        } if (view instanceof ImageView) {
            animate(view);
        } else {
            // Most views will not automatically change their state on click.
            if (view instanceof Checkable) {
                ((Checkable) view).toggle();
                view.setSelected(((Checkable) view).isChecked());
                ALog.d.tagMsg(this,"b checked=", ((Checkable) view).isChecked(), " selected=", view.isSelected());
            } else {
                view.setSelected(!view.isSelected());
                ALog.d.tagMsg(this, "b selected=",  view.isSelected());
            }
        }
    }

    private void animate(View view) {
        ImageView v = (ImageView) view;
        Drawable d = v.getDrawable();
        if (Build.VERSION.SDK_INT >= 21) {
            if (d instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
                avd.start();
                return;
            }
        }

        if (d instanceof AnimatedVectorDrawableCompat) {
            AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) d;
            avd.start();
        }
    }

    private void setup() {
        Ui.viewById(mRootView, R.id.avd_check1).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.avd_check2).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.avd_check3).setOnClickListener(this);
        animate(Ui.viewById(mRootView, R.id.avd_wx1_anim));
        animate(Ui.viewById(mRootView, R.id.avd_wx2_anim));
        animate(Ui.viewById(mRootView, R.id.avd_wx31_anim));
    }

}
