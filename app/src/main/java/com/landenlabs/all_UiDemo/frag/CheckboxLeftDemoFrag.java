package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landenlabs.all_UiDemo.R;

/**
 * Demonstrate Checkbox ui components with checkmark on left margin.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */
public class CheckboxLeftDemoFrag extends CheckboxDemoFrag {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.checkbox_left_demo, container, false);
        setup();
        return mRootView;
    }
}
