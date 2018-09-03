package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate Scrollview resizing with views above and below.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

public class ScrollResizeFrag  extends UiFragment implements View.OnClickListener {

    private View mRootView;
    private ViewGroup mDataHolder;
    private ScrollView mScrollView;
    private TextView mScrollIdxView;
    private String[] mDataArray;
    private int mDataIdx = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_scroll_resize, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.layout_scroll_resize_id;
    }

    @Override
    public String getName() {
        return "ScrollResize";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.scroll_add:
                addItem();
                break;
            case R.id.scroll_del:
                delItem();
                break;
        }
    }

    private void setup() {
        mDataHolder = Ui.viewById(mRootView, R.id.scroll_holder);
        mDataArray = getResources().getStringArray(R.array.states);

        Ui.viewById(mRootView, R.id.scroll_add).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.scroll_del).setOnClickListener(this);
        mScrollView = Ui.viewById(mRootView, R.id.resizing_scrollview);
        mScrollIdxView = Ui.viewById(mRootView, R.id.scroll_idx);

        int dataStartCnt = 5;
        while (mDataIdx < dataStartCnt)
            addItem();
    }

    private void addItem() {
        mDataIdx = mDataIdx % mDataArray.length;
        TextView tv = new TextView(mDataHolder.getContext());
        tv.setText(String.format("%3d %s", mDataIdx, mDataArray[mDataIdx++]));
        tv.setTextSize(20.0f);
        mDataHolder.addView(tv);
        mScrollView.scrollBy(0, 20);
        mScrollIdxView.setText(String.format("Idx:%d Cnt:%d", mDataIdx, mDataHolder.getChildCount()));
        // mScrollView.fullScroll(View.FOCUS_DOWN);
    }

    private void delItem() {
        int cnt = mDataHolder.getChildCount();
        if (cnt > 0) {
            mDataHolder.removeViewAt(0);
            mScrollIdxView.setText(String.format("Idx:%d Cnt:%d", mDataIdx, mDataHolder.getChildCount()));
        }
    }
}
