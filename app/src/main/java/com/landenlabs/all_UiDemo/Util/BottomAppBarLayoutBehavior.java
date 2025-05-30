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

package com.landenlabs.all_UiDemo.Util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.google.android.material.appbar.AppBarLayout;
import com.landenlabs.all_UiDemo.ALog.ALog;

/**
 * Dennis Lang on 5/17/16.
 * <p>
 * Hide AppBarLayout when at bottom of scroll and show when at top of scroll.
 * http://stackoverflow.com/questions/31830521/how-to-animate-both-top-and-bottom-toolbarsor-any-other-view-enter-exit-screen
 */
public class BottomAppBarLayoutBehavior extends CoordinatorLayout.Behavior<AppBarLayout> {
    // public class BottomAppBarLayoutBehavior extends AppBarLayout.Behavior {
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;

    public BottomAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super();
    }


    @Override
    public boolean onStartNestedScroll(
            @NonNull final CoordinatorLayout coordinatorLayout, @NonNull final AppBarLayout child,
            @NonNull final View directTargetChild, @NonNull final View target,
            final int nestedScrollAxes, @ViewCompat.NestedScrollType int type) {
        return true;
    }


    @Override
    public void onNestedPreScroll(
            @NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull
            View target, int dx, int dy, @NonNull
            int[] consumed,  @ViewCompat.NestedScrollType int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        ALog.d.tagMsg(this,  "onNestedPreScroll dy=", dy, " consumed[1]=" ,  consumed[1]);

        if (dy > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dy < 0 && child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        }
    }

    @Override
    public void onStopNestedScroll(
            @NonNull CoordinatorLayout coordinatorLayout, @NonNull AppBarLayout child, @NonNull
            View target, @ViewCompat.NestedScrollType int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
        ALog.d.tagMsg(this,  "StopNestedScroll");
    }

    /*
     int mOffset = 0;
           @Override
           public boolean setTopAndBottomOffset(int offset) {
               return super.setTopAndBottomOffset(mOffset);
           }

           @Override
           public int getTopAndBottomOffset() {
               return mOffset;
           }

    */
    @Override
    public void onNestedScroll(@NonNull final CoordinatorLayout coordinatorLayout,
                               @NonNull final AppBarLayout child,
                               @NonNull final View target, final int dxConsumed, final int dyConsumed,
                               final int dxUnconsumed, final int dyUnconsumed, @ViewCompat.NestedScrollType int type, @NonNull int[]out) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed, type, out);

        ALog.d.tagMsg(this,  "dyConsumed=", dyConsumed, " dyUnconsumed=",  dyUnconsumed);

        /*
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        }
        */

    }

    private void animateOut(final AppBarLayout appBarLayout) {
        ViewCompat.animate(appBarLayout).translationY(0F).alpha(0.0F).setInterpolator(INTERPOLATOR)
                .withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationStart(@NonNull View view) {
                        BottomAppBarLayoutBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationCancel(@NonNull View view) {
                        BottomAppBarLayoutBehavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(@NonNull View view) {
                        BottomAppBarLayoutBehavior.this.mIsAnimatingOut = false;
                        view.setVisibility(View.GONE);
                    }
                }).start();
    }

    private void animateIn(AppBarLayout appBarLayout) {
        appBarLayout.setVisibility(View.VISIBLE);
        ViewCompat.animate(appBarLayout).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start();

    }
}