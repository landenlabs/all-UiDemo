package com.landenlabs.all_UiDemo.frag;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate Radio Buttons
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */

public class RadioBtnFrag  extends UiFragment {

    private View mRootView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_radio_btns, container, false);

        setup();
        return mRootView;
    }



    @Override
    public int getFragId() {
        return R.id.layout_radio_btn_id;
    }

    @Override
    public String getName() {
        return "RadioBtn";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        addTabBar(Ui.<RadioGroup>viewById(mRootView, R.id.tab_holder1), 1.0f, 0);
        addTabBar(Ui.<RadioGroup>viewById(mRootView, R.id.tab_holder2), 0f, 10);
        addTabBar(Ui.<RadioGroup>viewById(mRootView, R.id.tab_holder2), 0f, 10);
        addTabBar(Ui.<RadioGroup>viewById(mRootView, R.id.tab_holder2), 0f, 10);
    }

    private void addTabBar(RadioGroup tabHolder, float weight, int padding) {
        final int maxTabs = 4;
        // tabHolder.removeAllViews();

        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{}
        };
        ColorStateList colorStateList = new ColorStateList(states,
                new int[]{0xff00ff00, 0x80ff0000});

        RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, weight);

        /*
        ViewOutlineProvider outlineBoundary;
        if (Build.VERSION.SDK_INT >= 21) {
            outlineBoundary = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRect(0, 0, 200, 60); // view.getMeasuredWidth(), view.getMeasuredHeight());
                }
            };
        }
        */

        String[] pageNames = new String[] { "Home", "Map", "Hourly", "Daily" };
        int tabCnt = 0;
        for (String pageName : pageNames) {
            RadioButton button = new RadioButton(tabHolder.getContext());

            String resName = "tab_" + pageName.toLowerCase();
            int resID = getResources().getIdentifier(resName, "drawable", getContextSafe().getPackageName());
            Drawable tabBtnIcon = getResources().getDrawable(resID);
            if (tabBtnIcon != null) {

                if (Build.VERSION.SDK_INT >= 21) {
                    tabBtnIcon.setTintMode(PorterDuff.Mode.MULTIPLY);
                    tabBtnIcon.setTintList(colorStateList);
                } else {
                    tabBtnIcon = DrawableCompat.wrap(tabBtnIcon);
                    DrawableCompat.setTintList(tabBtnIcon.mutate(), colorStateList);
                    DrawableCompat.setTintMode(tabBtnIcon.mutate(), PorterDuff.Mode.MULTIPLY);
                }

                // tabBtnIcon = tabHolder.getResources().getDrawable(android.R.drawable.btn_radio);
                if (Build.VERSION.SDK_INT >= 21) {
                    // Hide standard radio button but leave ripple effect
                    button.setButtonDrawable(null);
                } else {
                    // Hide standard radio button
                    button.setButtonDrawable(new StateListDrawable());
                }
                button.setCompoundDrawablesWithIntrinsicBounds(null, tabBtnIcon, null, null);

                /*
                // button.setBackgroundResource(R.drawable.ripple_boarderless);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {

                    button.setCompoundDrawablesWithIntrinsicBounds(null, tabBtnIcon, null, null);
                    // button.setCompoundDrawablesRelativeWithIntrinsicBounds(null, tabBtnIcon, null, null);
                    // tabBtnIcon.setBounds(0, 0, 50, 100); // img.getMinimumWidth(), img.getMinimumHeight());
                    // button.setCompoundDrawables(null, tabBtnIcon, null, null);
                } else {
                    button.setCompoundDrawablesWithIntrinsicBounds(null, tabBtnIcon, null, null);
                }
                */

                button.setBackgroundResource(R.drawable.ripple_boarderless);
                button.setPadding(padding, padding, padding, padding);
                button.setGravity(Gravity.CENTER);
                button.setTextColor(colorStateList);
                button.setText(pageName);

                tabHolder.addView(button, lp);

                if (++tabCnt == maxTabs)
                    break;
            }
        }
    }
}
