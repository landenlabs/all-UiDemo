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

package com.landenlabs.all_UiDemo.frag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        final boolean useLayout = false;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                view.setPressed(true);
                //noinspection ConstantConditions
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
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                //noinspection ConstantConditions
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
    }

    private void setOnTouch(ViewGroup viewGroup) {
        int cnt = viewGroup.getChildCount();
        for (int idx = 0; idx < cnt; idx++) {
            viewGroup.getChildAt(idx).setOnTouchListener(this);
        }
    }
}
