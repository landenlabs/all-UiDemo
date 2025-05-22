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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate Constraint Layout
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://LanDenLabs.com/android"> author's web-site </a>
 */

public class ConstraintFlowFrag extends UiFragment implements View.OnClickListener {

    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.constraint_flow, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.contraintlayout_id;
    }

    @Override
    public String getName() {
        return "OtherLayout";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        Ui.viewById(mRootView, R.id.constraint_btn1).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.constraint_btn2).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.constraint_btn3).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.constraint_btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.constraint_btn1) {
            setVis(R.id.h1_1, R.id.h2_1, R.id.h3_1, R.id.h4_1);
        } else if (id == R.id.constraint_btn2) {
            setVis(R.id.h1_2, R.id.h2_2, R.id.h3_2, R.id.h4_2, R.id.h5_2);
        } else if (id == R.id.constraint_btn3) {
            setVis(R.id.h1_3, R.id.h2_3, R.id.h3_3, R.id.h4_3, R.id.h5_3);
        } else if (id == R.id.constraint_btn4) {
            setVis(R.id.h1_4, R.id.h2_4, R.id.h3_4, R.id.h4_4, R.id.h5_4);
        }
    }

    private void setVis(int ... ids) {
        for (int id : ids) {
            View view = Ui.viewById(mRootView, id);
            switch (view.getVisibility()) {
                case View.VISIBLE:
                    view.setVisibility(View.INVISIBLE);
                    break;
                case View.INVISIBLE:
                    view.setVisibility(View.GONE);
                    break;
                case View.GONE:
                    view.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
}
