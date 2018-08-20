package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;


/**
 * Demonstrate GridLayout, Linear, Scroll, Percent layout of stuff.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */

public class OtherLayoutFrag  extends UiFragment implements View.OnClickListener {

    private View mRootView;

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
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.card_title_btn:
                setVis(Ui.viewById(mRootView, R.id.card_title));
                break;
            case R.id.card_more_btn:
                setVis(Ui.viewById(mRootView, R.id.card_more));
                break;
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
