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

package com.landenlabs.all_UiDemo.frag;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Scroll.ScrollAdapter;
import com.landenlabs.all_UiDemo.Scroll.ScrollItem;
import com.landenlabs.all_UiDemo.Scroll.ScrollItemPlanet;
import com.landenlabs.all_UiDemo.Ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrate ReyclerView layout of images.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

@SuppressWarnings("FieldCanBeLocal")
public class RecyclerFrag  extends UiFragment {

    private View mRootView;
    private RecyclerView mRecyclerView;
    private ScrollAdapter mAdapter;
    private ArrayList<ScrollItem> mScrollItems;

    private Spinner mSpinner1;
    private Spinner mSpinner2;

    // ---------------------------------------------------------------------------------------------

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

        // --- Recycler
        mRecyclerView = Ui.viewById(mRootView, R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mScrollItems = new ArrayList<>();
        mAdapter = new ScrollAdapter(getContext(), mScrollItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(false);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager layoutMgr =
                new LinearLayoutManager(mRecyclerView.getContext(), LinearLayoutManager.VERTICAL,
                        false);
        mRecyclerView.setLayoutManager(layoutMgr);

        int gapBetween = 40;
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull
                    RecyclerView parent,  @NonNull RecyclerView.State state) {
                outRect.set(0, gapBetween, 0, gapBetween);
            }
        });

        // --- Spinner1
        mSpinner1 = Ui.viewById(mRootView, R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.states, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter1);

        // --- Spinner2
        mSpinner2 = Ui.viewById(mRootView, R.id.spinner2);
        Spinner2Adapter adapter2 = new Spinner2Adapter(getContext(),
                android.R.layout.simple_spinner_item, mScrollItems);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner2.setAdapter(adapter2);

        createListData();

        // Update list - easiest way is to use adapter to update list
        adapter2.addAll(Spinner2Adapter.getList(mScrollItems));
        if (false) {
            // These do not cause list to update
            adapter2.notifyDataSetChanged();
            adapter2.notifyDataSetInvalidated();
            mSpinner2.invalidate();
        }
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

    private static class Spinner2Adapter extends ArrayAdapter<CharSequence> {

        public Spinner2Adapter(@NonNull Context context, int resource, @NonNull ArrayList<ScrollItem> scrollItems) {
            super(context, resource, getList(scrollItems));
        }

        public static List<CharSequence> getList(@NonNull  ArrayList<ScrollItem> scrollItems) {
            List<CharSequence> nameList = new ArrayList<>();
            for (ScrollItem item : scrollItems) {
                nameList.add(item.getName());
            }
            return nameList;
        }
    }
}
