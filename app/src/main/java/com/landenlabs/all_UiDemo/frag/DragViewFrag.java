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

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate dragging view child around container.
 *
 * https://stacktips.com/tutorials/android/how-to-drag-a-view-in-android
 * https://stackoverflow.com/questions/9398057/android-move-a-view-on-touch-move-action-move
 */
public class DragViewFrag extends UiFragment implements View.OnClickListener, View.OnTouchListener {

    // ---- Local Data ----
    private View mRootView;
    private View mDragView;
    private ViewGroup mDragHscrollHolder;
    private TextView mDragText;
    private int xDelta;
    private int yDelta;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.dragview_frag, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.dragview_id;
    }

    @Override
    public String getName() {
        return "DragView";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dragZoomIn:
                zoom(mDragView, +32);
                break;
            case R.id.dragZoomOut:
                zoom(mDragView, -32);
                break;
        }
    }

    private void zoom(View view, int size) {
        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        lParams.width = Math.max(64, Math.min(512, lParams.width + size));
        lParams.height = Math.max(64, Math.min(512, lParams.height + size));
        view.setLayoutParams(lParams);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        final boolean useLayout = false;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                view.setPressed(true);
                if (useLayout) {
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    xDelta = X - lParams.leftMargin;
                    yDelta = Y - lParams.topMargin;
                } else {
                    xDelta = X - (int)view.getX();
                    yDelta = Y - (int)view.getY();
                }

                break;
            case MotionEvent.ACTION_UP:
                view.setPressed(false);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
               if (useLayout) {
                   RelativeLayout.LayoutParams layoutParams =
                           (RelativeLayout.LayoutParams) view.getLayoutParams();
                   layoutParams.leftMargin = X - xDelta;
                   layoutParams.topMargin = Y - yDelta;
                   view.setLayoutParams(layoutParams);
               } else {
                   view.setX(X - xDelta);
                   view.setY(Y - yDelta);
               }
                mDragText.setText(String.format("X=%d, Y=%d", X, Y));
                break;
        }
        return true;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void setup() {
        mDragView = Ui.viewById(mRootView, R.id.dragView);
        mDragText = Ui.viewById(mRootView, R.id.dragText);
        mDragView.setOnTouchListener(this);
        Ui.viewById(mRootView, R.id.dragZoomIn).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.dragZoomOut).setOnClickListener(this);

        setOnTouch(Ui.viewById(mRootView, R.id.dragHlayout));

        mDragHscrollHolder = Ui.viewById(mRootView, R.id.dragHscrollHolder);
        mDragHscrollHolder.post(new Runnable() {
            @Override
            public void run() {
                setWidth(mDragHscrollHolder);
            }
        });

        HorizontalScrollView hScrollView = Ui.viewById(mRootView, R.id.dragHscroll);

        TabLayout tabLayout = Ui.viewById(mRootView, R.id.dragHscrollTab);

        hScrollView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int scrollX = view.getScrollX();
                int scrollY = view.getScrollY();

                int pos = 0;
                // float offset = (float)scrollX / hScrollView.getWidth();
                float offset = HScrollPercentAt(hScrollView, scrollX);
                Log.d("xxDen", String.format("X=%d  Y=%d pos=%d off=%f", scrollX, scrollY, pos, offset));

                tabLayout.setScrollPosition(0, offset, false);
                return false;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int x = hScrollView.getScrollX();
                int y = hScrollView.getScrollY();
                // hScrollView.scrollTo(tab.getPosition() * screenWidthPx, y);
                hScrollView.scrollTo( getSumWidth(mDragHscrollHolder, tab.getPosition()), y);
                Log.d("xxDen", String.format("Tab X=%d pos=%d", x, tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setOnTouch(ViewGroup viewGroup) {
        int cnt = viewGroup.getChildCount();
        for (int idx = 0; idx < cnt; idx++) {
            viewGroup.getChildAt(idx).setOnTouchListener(this);
        }
    }

    private float HScrollPercentAt(HorizontalScrollView hScrollView, int pos) {
        ViewGroup scrollHolder = (ViewGroup)hScrollView.getChildAt(0);
        int cnt = scrollHolder.getChildCount();
        int sumWidth = 0;
        int itemWidth = 1;
        float percent = 0;
        for (int idx = 0; idx < cnt; idx++) {
            View view = scrollHolder.getChildAt(idx);
            LinearLayout.LayoutParams
                    lparm = (LinearLayout.LayoutParams)view.getLayoutParams();
            itemWidth = lparm.leftMargin + lparm.rightMargin;
            itemWidth += view.getWidth();
            if (pos < sumWidth + itemWidth)
                break;
            sumWidth += itemWidth;
            percent += 1;
        }
        percent += (float)(pos - sumWidth) / itemWidth;
        return percent;
    }

    private int getSumWidth(ViewGroup scrollHolder, int pos) {
        // int cnt = viewGroup.getChildCount();
        /*
        FrameLayout.LayoutParams parm =
        (FrameLayout.LayoutParams)viewGroup.getLayoutParams();
        int margin = parm.leftMargin + parm.rightMargin;
        margin = viewGroup.getPaddingLeft() + viewGroup.getPaddingRight();
        */

        int sumWidth = 0;
        for (int idx = 0; idx < pos; idx++) {
            View view = scrollHolder.getChildAt(idx);
            LinearLayout.LayoutParams
                lparm = (LinearLayout.LayoutParams)view.getLayoutParams();
            sumWidth += lparm.leftMargin + lparm.rightMargin;
            sumWidth += view.getWidth();
        }
        return sumWidth;
    }

    private void setWidth(ViewGroup scrollHolder) {
        ViewGroup parentContainer = (ViewGroup)scrollHolder.getParent();
        int parentWidth = parentContainer.getWidth() -  parentContainer.getPaddingLeft() - parentContainer.getPaddingRight();

        int cnt = scrollHolder.getChildCount();
        for (int idx = 0; idx < cnt; idx++) {
            View view = scrollHolder.getChildAt(idx);
            LinearLayout.LayoutParams parm =
                    (LinearLayout.LayoutParams)view.getLayoutParams();
            int width = view.getWidth();
            width = Math.max(parentWidth - parm.leftMargin - parm.rightMargin, width);
            parm.width = width;
            view.setLayoutParams(parm);

            /*
            LinearLayout.LayoutParams parm =
                    (LinearLayout.LayoutParams)view.getLayoutParams();
            int w = view.getWidth();
            w = view.getMeasuredWidth();
            int p = view.getPaddingLeft() + view.getPaddingRight();
            parm.width = viewGroup.getWidth();
            parm.width = childWidth;

            view.setLayoutParams(parm);
            */
        }
    }
}
