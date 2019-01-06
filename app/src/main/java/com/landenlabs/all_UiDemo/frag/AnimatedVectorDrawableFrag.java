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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Created by Dennis Lang on 10/5/17.
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
            Log.d("fxx", "a checked=" + ((CompoundButton) view).isChecked() + " selected=" + view.isSelected());
        } else {
            // Most views will not automatically change their state on click.
            if (view instanceof Checkable) {
                ((Checkable) view).toggle();
                view.setSelected(((Checkable) view).isChecked());
                Log.d("fxx", "b checked=" + ((Checkable) view).isChecked() + " selected=" + view.isSelected());
            } else {
                view.setSelected(!view.isSelected());
                Log.d("fxx", "b selected=" + view.isSelected());
            }
        }
    }


    private void setup() {
        Ui.viewById(mRootView, R.id.avd_check1).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.avd_check2).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.avd_check3).setOnClickListener(this);
    }

}
