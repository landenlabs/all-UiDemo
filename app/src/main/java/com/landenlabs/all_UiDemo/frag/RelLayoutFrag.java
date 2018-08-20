package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landenlabs.all_UiDemo.R;

/**
 * Demonstrate grid layout of images.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */

public class RelLayoutFrag  extends UiFragment {

    @SuppressWarnings("FieldCanBeLocal")
    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.rellayout, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.rel_layout_id;
    }

    @Override
    public String getName() {
        return "RelLayout";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
    }

}
