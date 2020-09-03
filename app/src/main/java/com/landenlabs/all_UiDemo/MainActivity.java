package com.landenlabs.all_UiDemo;

/*
 * Copyright (c) 2019 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (3/21/2015)
 *  @see http://landenlabs.com
 *
 */

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.landenlabs.all_UiDemo.ALog.ALog;
import com.landenlabs.all_UiDemo.ALog.UncaughtExceptionHandler;
import com.landenlabs.all_UiDemo.Util.AppCrash;
import com.landenlabs.all_UiDemo.Util.GoogleAnalyticsHelper;
import com.landenlabs.all_UiDemo.Util.PageItem;


/**
 * Main Activity to Ui Demo app.
 *
 * App can be started with deep link
 *   adb shell am start -W -a android.intent.action.VIEW -d "landenlabs://alluidemo/page1"
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */
@SuppressWarnings("ConstantIfStatement")
public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ActionBar mActionBar;
    private Parcelable mAdapterParcelable;
    private int mStartPageIdx = -1; // -1 = menu, else 0...n-1 pages

    private GoogleAnalyticsHelper mAnalytics;

    private UncaughtExceptionHandler m_uncaughtExceptionHandler;


    private static boolean isDebug(ApplicationInfo appInfo) {
        return ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
    }

    // ---------------------------------------------------------------------------------------------
    //  App can be started with deep link
    //    adb shell am start -W -a android.intent.action.VIEW -d "landenlabs://alluidemo/page1"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean DEBUG = isDebug(getApplicationInfo());
        AppCrash.initalize(getApplication(), DEBUG);
        ALog.minLevel = (DEBUG ? ALog.VERBOSE : ALog.NOLOGGING);
        mAnalytics = new GoogleAnalyticsHelper(getApplication(), DEBUG);

        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle("UiDemo API" + Build.VERSION.SDK_INT +
                    (BuildConfig.DEBUG ? " Dbg" : ""));

            mActionBar.setSubtitle(BuildConfig.VERSION_NAME);
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);
            mActionBar.setIcon(R.drawable.uidemo_sm);
        }

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.pager);
        // TODO - call removeOnPageChangeListener
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (mActionBar != null && mSectionsPagerAdapter != null) {
                    mActionBar.setSubtitle(mSectionsPagerAdapter.getPageTitle(position));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // App can be started with deep link.
        //    adb shell am start -W -a android.intent.action.VIEW -d "landenlabs://alluidemo/page1"
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
        ALog.d.tagMsg(this, "create action=", action, " data=", data);
        if (data != null) {
            String page = data.getPath();   // ex:  /page1
            if (!TextUtils.isEmpty(page) && page.startsWith("/page")) {
                page = page.replace("/page", "");
                try {
                    mStartPageIdx = Integer.parseInt(page);
                    ALog.d.tagMsg(this, "create select page=", mStartPageIdx);
                } catch (NumberFormatException ignore) {
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        m_uncaughtExceptionHandler = new UncaughtExceptionHandler(this);
    }

    @SuppressWarnings({"SingleStatementInBlock", "ConstantConditions"})
    @Override
    protected void onResume() {
        super.onResume();
        if (mViewPager != null && mViewPager.getAdapter() == null) {
            // Create the adapter that will return a fragment for each page.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mSectionsPagerAdapter);
            if (false) {
                // Optionally set limit of pages to keep.
                mViewPager.setOffscreenPageLimit(mSectionsPagerAdapter.getCount());
            }
            if (mAdapterParcelable != null) {
                mSectionsPagerAdapter.restoreState(mAdapterParcelable, null);
            }

            if (mStartPageIdx >= 0 && mStartPageIdx < mSectionsPagerAdapter.getCount()) {
                ALog.d.tagMsg(this, "Select page=", mStartPageIdx);
                selectPage(mStartPageIdx);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSectionsPagerAdapter != null)
            mAdapterParcelable = mSectionsPagerAdapter.saveState();
        mSectionsPagerAdapter = null;
        if (mViewPager != null)
             mViewPager.setAdapter(null);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    SubMenu mPageMenu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mPageMenu = menu.addSubMenu("Pages...");
        int groupId = 1;
        int itemId = 100;
        for (PageItem item : mItems) {
            mPageMenu.add(groupId, itemId, itemId, item.mTitle);
            itemId++;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id >= 100 && id < 100 + mItems.length) {
            selectPage(id - 100);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 0) {
            super.onBackPressed(); // This will pop the Activity from the stack.
        } else {
            selectPage(0);
        }
    }

    public void selectPage(int idx) {
        mViewPager.setCurrentItem(idx);
    }

    public static PageItem[] getPageItems() {
        return mItems;
    }

    // =============================================================================================

    private static final PageItem[] mItems = new PageItem[] {

            new PageItem( "Menu", R.layout.page_menu_frag),

           // new PageItem( "Bounded views", R.layout.page_bounded_size),

            // new PageItem( "DrawerLayout", R.layout.page_drawer_layout),
            new PageItem( "Scroll Resize", R.layout.page_scroll_resize),
            new PageItem( "Expand List", R.layout.page_expandable_list),

            new PageItem( "Assorted", R.layout.page_assorted),
            new PageItem( "Text Alignment", R.layout.page_text),
            new PageItem( "TextSize", R.layout.page_text_height),

            new PageItem( "GridView Images", R.layout.page_grid_image),
            new PageItem( "GridLayout", R.layout.page_grid_layout),
            new PageItem( "Image Scales", R.layout.page_image_scales),
            new PageItem( "Image Overlap",  R.layout.page_image_over ),
            new PageItem( "Image Blend",  R.layout.page_image_blend ),
            new PageItem( "Drawables",  R.layout.page_drawables ),

            new PageItem( "RadioBtn Tabs", R.layout.page_radio_btns),
            new PageItem( "RadioBtn List", R.layout.page_radio_list),
            new PageItem( "TextView+Image", R.layout.page_textviews),
            new PageItem( "CkBox List", R.layout.page_list1),       // min api 21
            new PageItem( "Custom List",  R.layout.page_anim_list ),
            new PageItem( "RecyclerView", R.layout.page_recyclerview),
            new PageItem( "Picker", R.layout.pickers),

            new PageItem( "Toggle/Switch",  R.layout.page_switches),
            new PageItem( "Checkbox Right",  R.layout.page_checkbox_right ),
            new PageItem( "Checkbox Left",  R.layout.page_checkbox_left ),

            new PageItem( "Animated Vector",  R.layout.page_animated_vector_drawable ),
            new PageItem( "Animation",  R.layout.page_animation ),
            new PageItem( "Anim Bg", R.layout.page_anim_bg_frag),

            new PageItem( "SeekBar Hz", R.layout.page_seekbar_horz),
            new PageItem( "SeekBar Vt", R.layout.page_seekbar_vert),
            new PageItem( "Screen Draw", R.layout.page_screen),
            new PageItem( "DragView", R.layout.page_dragview),

            new PageItem( "Relative Layout",  R.layout.page_rellayout ),
            new PageItem( "Constraint Layout ", R.layout.page_contraintlayout_frag),
            new PageItem( "Other Layout",  R.layout.page_otherlayout_frag ),
            new PageItem( "Bounded views", R.layout.page_bounded_size),
            new PageItem( "Scaled Btn", R.layout.page_scaled_image_btn),
            new PageItem( "Layout Anim",  R.layout.page_layout_anim ),
            new PageItem( "Full Screen",  R.layout.page_fullscreen ),
            new PageItem( "Rotate Screen", R.layout.page_rotatescreen ),

            // API 21
            new PageItem( "ElevShadow (API21)",  R.layout.page_elevation ),
            new PageItem( "Coordinated (API21)", R.layout.page_coordinated ),
            new PageItem( "TabLayout (API21)", R.layout.page_tablayout),
            new PageItem( "Ripple", R.layout.page_ripple),

            new PageItem( "GL Cube", R.layout.page_glcube_frag),
            new PageItem( "Graph Line", R.layout.page_graphline_frag),

            new PageItem( "View Shadows", R.layout.page_shadows),
            new PageItem( "Render Blur", R.layout.page_renderscript)
    };

    // =============================================================================================
    // SectionsPagerAdapter - implement page swipes
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    static class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_SET_USER_VISIBLE_HINT);

            // ActionBar actionBar = getActionBar();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PageFragment (defined as a static inner class below).
            return PageFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mItems.length;  // View swipe page count.
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mItems[position].mTitle;
        }
    }

    /**
     * A page fragment with selectable layouts per page number.
     */
    public static class PageFragment extends Fragment {
        // The fragment argument representing the section number for this  fragment.
        static final String ARG_page_number = "page_number";
        int m_pageNum = 0;

        // Returns a new instance of this fragment for the given section  number.
        static PageFragment newInstance(int sectionNumber) {
            PageFragment fragment = new PageFragment();

            Bundle args = new Bundle();
            args.putInt(ARG_page_number, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(
                @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            m_pageNum = (getArguments() == null) ? 0 : getArguments().getInt(ARG_page_number);

            int layout =  mItems[m_pageNum].mLayout;
            try {
                return inflater.inflate(layout, container, false);
            } catch (Exception ex) {
                View rootView = inflater.inflate(R.layout.page_exception,  container, false);
                rootView.setId(View.generateViewId());
                TextView tv = Ui.viewById(rootView, R.id.exception_text);
                tv.setText(ex.getLocalizedMessage() + "\n" + ex.getCause() + "\n Maybe due to device API not compatible\n");
                return rootView;
            }
        }
    }
}
