package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate animated layout of images.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

public class AnimLayoutFrag  extends UiFragment {

    private View mRootView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_anim, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.layout_anim_id;
    }

    @Override
    public String getName() {
        return "AnimLayout";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        ViewGroup mLayout1 = Ui.viewById(mRootView, R.id.layout_anim1);
        ViewGroup mLayout2 = Ui.viewById(mRootView, R.id.layout_anim2);
        ViewGroup mLayout3 = Ui.viewById(mRootView, R.id.layout_anim3);
        ViewGroup mLayout4 = Ui.viewById(mRootView, R.id.layout_anim4);
        ViewGroup mLayout5 = Ui.viewById(mRootView, R.id.layout_anim5);

        updateLayout(mLayout1, 0xffff0000, 5, 5, 1);
        updateLayout(mLayout2, 0xff00ffff, 3, 6, 2);
        updateLayout(mLayout3, 0xff0000ff, 8, 8, 1);
        updateLayout(mLayout4, 0xffff00ff, 8, 8, 1);
        updateLayout(mLayout5, 0xffffff00, 8, 8, 1);
    }

    private void updateLayout(final ViewGroup layout, final int bgColor, final int minChild, final int maxChild, final int sec) {
        int childCnt = layout.getChildCount();

        if (childCnt < maxChild) {
            TextView tv = new TextView(layout.getContext());
            tv.setText("Test Text # " + layout.getChildCount());
            tv.setTextColor(bgColor);
            tv.setTextSize(30.0f);
            layout.addView(tv);
        } else {
            while (childCnt > minChild) {
                layout.removeViewAt(0);
                childCnt--;
            }
        }

        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLayout(layout, bgColor, minChild, maxChild, sec);
            }
        }, sec * 1000);
    }

}
