package com.landenlabs.all_UiDemo.Util;

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
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.landenlabs.all_UiDemo.ALog.ALog;

/**
 * Pair a Tablayout with HorizontalScrollView
 */
@SuppressWarnings("Convert2Lambda")
public class TabPair {

    @SuppressLint("ClickableViewAccessibility")
    public static void init(TabLayout tabLayout, HorizontalScrollView hScrollView ) {
        ViewGroup dragHscrollHolder = (ViewGroup)hScrollView.getChildAt(0);

        hScrollView.post(new Runnable() {
            @Override
            public void run() {
                setHscrollItemWidth(dragHscrollHolder);
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            hScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                        int oldScrollY) {
                    // int scrollX = view.getScrollX();
                    float offset = HScrollPercentAt(hScrollView, scrollX);
                    ALog.d.tagMsg(this,  String.format("X=%d  off=%f", scrollX, offset));
                    tabLayout.setScrollPosition(0, offset, false);
                }
            });
        } else {
            hScrollView.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    int scrollX = view.getScrollX();
                    float offset = HScrollPercentAt(hScrollView, scrollX);
                    ALog.d.tagMsg(this,  String.format("X=%d  off=%f", scrollX, offset));
                    tabLayout.setScrollPosition(0, offset, false);
                    return false;
                }
            });
        }

        // BaseOnTabSelectedListener Not available in suppoert library version v27.1.1
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int y = hScrollView.getScrollY();
                hScrollView.scrollTo( getSumWidth(dragHscrollHolder, tab.getPosition()), y);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    private static float HScrollPercentAt(HorizontalScrollView hScrollView, int pos) {
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

    private static int getSumWidth(ViewGroup scrollHolder, int pos) {
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

    private static  void setHscrollItemWidth(ViewGroup scrollHolder) {
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
        }
    }
}
