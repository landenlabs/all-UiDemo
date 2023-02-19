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
package com.landenlabs.all_UiDemo.Util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.landenlabs.all_UiDemo.ALog.ALog;
import com.landenlabs.all_UiDemo.R;

// Original version from https://github.com/krishnalalstha/ExpandableLayout

public class ExpandablePanel extends LinearLayout {

    private int mHandleId;
    private int mContentId;

    // Contains references to the handle and content views
    private View mHandle;
    private View mContent;

    // Does the panel start expanded?
    private boolean mExpanded = false;
    // The height of the content when collapsed
    private int mCollapsedDim = 0;
    // The full expanded dimension of the content (calculated)
    private int mContentDim = 0;
    // How long the expand animation takes
    private int mAnimationDuration = 0;

    private final ExpandAnimation mAnimation = new ExpandAnimation();

    // Listener that gets fired onExpand and onCollapse
    private OnExpandListener mListener;

    public ExpandablePanel(Context context) {
        this(context, null);
    }

    public ExpandablePanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ExpandablePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandablePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
            int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void expand(boolean doCollapse) {
        mExpanded = doCollapse;
        mHandle.performClick();
    }

    /**
     * The constructor simply validates the arguments being passed in and
     * sets the global variables accordingly.
     * Required attributes are 'handle' and 'content'
     */
    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.ExpandablePanel, 0, 0);

        mCollapsedDim = (int) a.getDimension(
                R.styleable.ExpandablePanel_collapsedDim, 0.0f);

        // How long the animation should take
        mAnimationDuration = a.getInteger(
                R.styleable.ExpandablePanel_animationDuration, 500);

        int handleId = a.getResourceId(R.styleable.ExpandablePanel_handle, 0);
        if (handleId == 0) {
            throw getException("handle");
        }

        int contentId = a.getResourceId(R.styleable.ExpandablePanel_content, 0);
        if (contentId == 0) {
            contentId = getId();
            // throw getException("content");
        }

        mHandleId = handleId;
        mContentId = contentId;

        mExpanded = a.getBoolean(R.styleable.ExpandablePanel_expanded, true);
        a.recycle();

        mAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    ALog.i.tagMsg(this,  "onAnimationStart");
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ALog.i.tagMsg(this,  "onAnimationEnd");
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    ALog.i.tagMsg(this,  "onAnimationRepeat");
                }
            }
        );
    }

    // Some public setters for manipulating the
    // ExpandablePanel programmatically
    public void setOnExpandListener(OnExpandListener listener) {
        mListener = listener;
    }

    public void setCollapsedDimension(int collapsedDim) {
        mCollapsedDim = collapsedDim;
    }

    public void setAnimationDuration(int animationDuration) {
        mAnimationDuration = animationDuration;
    }

    @Override
    public Animation getAnimation() {
        return mAnimation;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mHandle = findViewThrowIfNull(mHandleId, "handle");
        mHandle.setOnClickListener(new PanelToggler());


        mContent = findViewThrowIfNull(mContentId, "content");

        // Change dimension of the content starting with it collapsed
        if (!mExpanded) {
            android.view.ViewGroup.LayoutParams lp = mContent.getLayoutParams();
            if (getOrientation() == VERTICAL) {
                lp.height = mCollapsedDim;
            } else {
                lp.width = mCollapsedDim;
            }
            mContent.setLayoutParams(lp);
        }

        /*
        if (mContentDim < mCollapsedDim) {
            mHandle.setVisibility(View.GONE);
        } else {
            mHandle.setVisibility(View.VISIBLE);
        }
        */
    }

    private IllegalArgumentException getException(String why) {
        return new IllegalArgumentException(
                "The " + why + " attribute is required and must refer to a valid child.");
    }

    private View findViewThrowIfNull(int resId, String what) {
        View view = findViewById(resId);
        if (resId == -1 || resId == getId()) {
            view = this;
        }
        if (view == null) {
            if (getParent() instanceof ViewGroup) {
                view = ((ViewGroup) getParent()).findViewById(resId);
            }
            if (view == null) {
                view = getRootView().findViewById(resId);
            }
            if (view == null) {
                throw getException(what);
            }
        }
        return view;
    }

    /**
     * This is where the magic happens for measuring the actual
     * (un-expanded) height of the content. If the actual height
     * is less than the collapsedHeight, the handle will be hidden.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // First, measure dimension content wants to be

        if (getOrientation() == VERTICAL) {
            if (mContent != this)
                mContent.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
             mContentDim = Math.max(mContentDim, mContent.getMeasuredHeight());
        } else {
            if (mContent != this)
                mContent.measure(MeasureSpec.UNSPECIFIED, heightMeasureSpec);
             mContentDim = Math.max(mContentDim, mContent.getMeasuredWidth());
        }

        /*
        if (mContentDim < mCollapsedDim) {
            mHandle.setVisibility(View.GONE);
        } else {
            mHandle.setVisibility(View.VISIBLE);
        }
        */

        // Then let the usual thing happen
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Simple OnExpandListener interface
     */
    protected interface OnExpandListener {
        void onExpand(View handle, View content);

        void onCollapse(View handle, View content);
    }

    /**
     * This is the on click listener for the handle.
     * It basically just creates a new animation instance and fires
     * animation.
     */
    private class PanelToggler implements OnClickListener {
        public void onClick(View v) {
            // Animation animation;
            if (mExpanded) {
                mAnimation.setStartEndOrientation(mContentDim, mCollapsedDim, getOrientation());
                // animation = new ExpandAnimation(mContentDim, mCollapsedDim, getOrientation());
                if (mListener != null) {
                    mListener.onCollapse(mHandle, mContent);
                }
            } else {
                mAnimation.setStartEndOrientation(mCollapsedDim, mContentDim, getOrientation());
                // animation = new ExpandAnimation(mCollapsedDim, mContentDim, getOrientation());
                if (mListener != null) {
                    mListener.onExpand(mHandle, mContent);
                }
            }

            mAnimation.setDuration(mAnimationDuration);
            mContent.startAnimation(mAnimation);
            mExpanded = !mExpanded;

            /*
            if (mContentDim < mCollapsedDim) {
                mHandle.setVisibility(View.GONE);
            } else {
                mHandle.setVisibility(View.VISIBLE);
            }
            */
        }
    }

    /**
     * This is a private animation class that handles the expand/collapse
     * animations. It uses the animationDuration attribute for the length
     * of time it takes.
     */
    private class ExpandAnimation extends Animation {
        private int mStartDim;
        private int mDeltaDim;
        private int mOrientation;

        ExpandAnimation() {
        }

        public ExpandAnimation(int startDim, int endDim, int orientation) {
            mStartDim = startDim;
            mDeltaDim = endDim - startDim;
            mOrientation = orientation;
        }

        void setStartEndOrientation(int startDim, int endDim, int orientation) {
            mStartDim = startDim;
            mDeltaDim = endDim - startDim;
            mOrientation = orientation;
            // applyTransformation(0, null);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation trans) {
            if (mDeltaDim != 0) {
                android.view.ViewGroup.LayoutParams lp = mContent.getLayoutParams();
                int value = (int) (mStartDim + mDeltaDim * interpolatedTime);
                boolean change;
                if (mOrientation == VERTICAL) {
                    change = (value != lp.height);
                    lp.height = value;
                } else {
                    change = (value != lp.width);
                    lp.width = value;
                }
                if (change) {
                    ALog.d.tagMsg(this,
                            "applyTransformation startDim=" + mStartDim + " deltaDim=" + mDeltaDim + " value=" + value);
                    mContent.setLayoutParams(lp);
                }
            } // else {
                // ALog.d.tagMsg(this, "applyTransformation ignored");
            // }

        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}