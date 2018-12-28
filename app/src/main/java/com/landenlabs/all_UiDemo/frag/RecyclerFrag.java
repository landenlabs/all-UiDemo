package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Scroll.ScrollAdapter;
import com.landenlabs.all_UiDemo.Scroll.ScrollItem;
import com.landenlabs.all_UiDemo.Scroll.ScrollItemPlanet;
import com.landenlabs.all_UiDemo.Ui;

import java.util.ArrayList;

/**
 * Demonstrate ReyclerView layout of images.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

public class RecyclerFrag  extends UiFragment {

    @SuppressWarnings("FieldCanBeLocal")
    private View mRootView;
    private RecyclerView mRecyclerView;
    private ScrollAdapter mAdapter;
    private ArrayList<ScrollItem> mScrollItems;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.recycler, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.recycler_view_id;
    }

    @Override
    public String getName() {
        return "Recycler";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {

        mRecyclerView = Ui.viewById(mRootView, R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mScrollItems = new ArrayList<>();
        mAdapter = new ScrollAdapter(getContext(), mScrollItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);

        createListData();
    }

    private void createListData() {
        mScrollItems.add(new ScrollItemPlanet("Earth", 150, 10, 12750));
        mScrollItems.add(new ScrollItemPlanet("Jupiter", 778, 26, 143000));
        mScrollItems.add(new ScrollItemPlanet("Mars", 228, 4, 6800));
        mScrollItems.add(new ScrollItemPlanet("Pluto", 5900, 1, 2320));
        mScrollItems.add(new ScrollItemPlanet("Venus", 108, 9, 12750));
        mScrollItems.add(new ScrollItemPlanet("Saturn", 1429, 11, 120000));
        mScrollItems.add(new ScrollItemPlanet("Mercury", 58, 4, 4900));
        mScrollItems.add(new ScrollItemPlanet("Neptune", 4500, 12, 50500));
        mScrollItems.add(new ScrollItemPlanet("Uranus", 2870, 9, 52400));

        mAdapter.notifyDataSetChanged();
    }
}
