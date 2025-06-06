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

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

import java.util.ArrayList;


/**
 * Demonstrate GridLayout, Linear, Scroll, Percent layout of stuff.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://LanDenLabs.com/android"> author's web-site </a>
 */

@SuppressWarnings("FieldCanBeLocal")
public class OtherLayoutFrag  extends UiFragment
        implements View.OnClickListener {

    private View mRootView;
    private ArrayList<View> childList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.otherlayout_frag, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.otherlayout_id;
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
        Ui.viewById(mRootView, R.id.card_title_btn).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.card_more_btn).setOnClickListener(this);

        TableLayout tableLayout = Ui.viewById(mRootView, R.id.other_tableLayout);
        childList = new ArrayList<>();
        getChildren(tableLayout, childList);
        int[] randomIdx = { 1, 3, 7, 11, 13 };
        for (int idx : randomIdx) {
            if (idx < childList.size()) {
                View view = childList.get(idx);
                view.setBackgroundResource(R.drawable.anim_grady1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ((AnimatedVectorDrawable)view.getBackground()).start();
                }
                view.setOnClickListener(this);
            }
        }

        Context context = requireContext();
        GridLayout gridLayout1 =
            Ui.viewById(mRootView, R.id.gridLayoutFillProgramatically);

        // gridLayout1.removeAllViews();

        for (int row = 0; row < 3; row++) {
            TextView tvLeft1 = new TextView(new ContextThemeWrapper(context, R.style.info_label));
            tvLeft1.setText("Top Left Text " + row);
            tvLeft1.setBackgroundColor(0xffccffcc);
            TextView tvLeft2 = new TextView(new ContextThemeWrapper(context, R.style.info_label));
            tvLeft2.setText("Bottom Left " + row);
            tvLeft2.setBackgroundColor(0xffffcccc);
            TextView tvRight = new TextView(new ContextThemeWrapper(context, R.style.info_value));
            tvRight.setText("Right 2 rows " + row);
            tvRight.setBackgroundColor(0xffccccff);
            View line = new View(context);
            line.setBackgroundColor(Color.GREEN);

            GridLayout.LayoutParams txLp = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            txLp.columnSpec = GridLayout.spec(0, 1, 1f);  //  GridLayout.UNDEFINED
            gridLayout1.addView(tvLeft1, txLp);

            txLp = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            txLp.columnSpec = GridLayout.spec(1, 1, 1f);  //  GridLayout.UNDEFINED
            txLp.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 2);
            txLp.setGravity(Gravity.CENTER_VERTICAL);
            gridLayout1.addView(tvRight, txLp);

            txLp = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            txLp.columnSpec = GridLayout.spec(0, 1, 1f);  //  GridLayout.UNDEFINED
            gridLayout1.addView(tvLeft2, txLp);

            txLp = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4));
            txLp.columnSpec = GridLayout.spec(0, 2, 1f);  //  GridLayout.UNDEFINED
            txLp.setMargins(4, 10, 4, 10);
            gridLayout1.addView(line, txLp);
        }

    }

    void getChildren(ViewGroup viewGroup, ArrayList<View> childList) {
        int cnt = viewGroup.getChildCount();
        for (int idx = 0; idx < cnt; idx++) {
            View child = viewGroup.getChildAt(idx);
            if (child instanceof  ViewGroup) {
                getChildren((ViewGroup)child, childList);
            } else {
                childList.add(child);
            }
        }
    }

    final AutoTransition autoTransition = new AutoTransition();
    static final int INCR = 80;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.card_title_btn) {
            setVis(Ui.viewById(mRootView, R.id.card_title));
        } else if (id == R.id.card_more_btn) {
            setVis(Ui.viewById(mRootView, R.id.card_more));
        } else {// view.setBackgroundColor(Color.GREEN);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            autoTransition.setDuration(3000);
            TransitionManager.beginDelayedTransition((ViewGroup) mRootView, autoTransition);
            params.width += INCR;
            params.height += INCR;
            view.requestLayout();
            view.invalidate();
        }
    }

    private void setVis(View view) {

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
