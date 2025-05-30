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
 * @see https://LanDenLabs.com/
 */

package com.landenlabs.all_UiDemo.frag;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;
import com.landenlabs.all_UiDemo.Util.DrawView;

import java.util.Locale;

/**
 * Display screen pixel and DP dimensions.
 *
 * @author Dennis Lang
 *
 */
@SuppressWarnings("FieldCanBeLocal")
public class ScreenFrag extends UiFragment {

    private FragmentActivity m_context;
    private View m_rootView;

    private LinearLayout m_layout;
    private TextView m_screenDevice;
    private ImageView m_horzWindowArrow;
    private ImageView m_vertPanelArrow;
    private TextView m_horzWindowText;
    private TextView m_vertPanelText;
    private DrawView m_drawPoints;

    private DisplayMetrics m_displayMetrics;

    static final int MSG_GET_UI_SIZE = 1;
    private final Handler m_handler = new Handler() {

        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_GET_UI_SIZE:
                    setPanelSize();
                    break;
            }
        }
    };

    public ScreenFrag() {
    }

    public static ScreenFrag create() {
        return new ScreenFrag();
    }

    // ============================================================================================
    // Override DevFragment

    @Override
    public int getFragId() {
        return R.id.screen_id;
    }

    @Override
    public String getName() {
        return "Screen";
    }

    @Override
    public String getDescription() {
        return "??";
    }


    // ============================================================================================

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateView();
    }

    // ============================================================================================

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        
        m_context = this.getActivity();
        //	m_context.setTheme(R.style.Theme_TranslucentActionBar_ActionBar_Overlay);
        m_rootView = inflater.inflate(R.layout.screen_frag, container, false);

        updateView();

        return m_rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    // ============================================================================================
    // Local methods

    void updateView() {
        m_displayMetrics = Ui.getDisplayMetrics(m_context);

        m_layout = Ui.viewById(m_rootView, R.id.screen_layout);
        m_screenDevice = Ui.viewById(m_rootView, R.id.screen_device);

        TextView screenSizeText = Ui.viewById(m_rootView, R.id.screen_size);
        TextView screenDensityText = Ui.viewById(m_rootView, R.id.screen_density);

        m_screenDevice.setText(Build.MODEL);
        int widthPx = m_displayMetrics.widthPixels;
        int heightPx = m_displayMetrics.heightPixels;
        String sizeStr = String.format(Locale.getDefault(),
                "%d dp x %d dp\n%d px x %d px\n%.1f in x %.1f in",
                Ui.pxToDp(widthPx), Ui.pxToDp(heightPx),
                widthPx, heightPx,
                (float) widthPx / m_displayMetrics.densityDpi,
                (float) heightPx / m_displayMetrics.densityDpi);
        screenSizeText.setText(sizeStr);

        String densityStr;
        if (m_displayMetrics.densityDpi <= DisplayMetrics.DENSITY_MEDIUM)
            densityStr = "Medium";
        else if (m_displayMetrics.densityDpi <= DisplayMetrics.DENSITY_HIGH)
            densityStr = "High";
        else if (m_displayMetrics.densityDpi <= DisplayMetrics.DENSITY_XHIGH)
            densityStr = "x-High";
        else if (m_displayMetrics.densityDpi <= DisplayMetrics.DENSITY_XXHIGH)
            densityStr = "xx-High";
        else if (m_displayMetrics.densityDpi <= DisplayMetrics.DENSITY_XXXHIGH)
            densityStr = "xxx-High";
        else
            densityStr = "xxxx-High";

        screenDensityText.setText(String.format("Density %s(%d) px/dp=%.2f",
                densityStr, m_displayMetrics.densityDpi, m_displayMetrics.density));

        m_horzWindowArrow = Ui.viewById(m_rootView, R.id.horz_arrow);
        m_vertPanelArrow = Ui.viewById(m_rootView, R.id.vert_panel_arrow);

        m_horzWindowText = Ui.viewById(m_rootView, R.id.horz_arrow_text);
        m_vertPanelText = Ui.viewById(m_rootView, R.id.vert_panel_text);


        /*
         * Dynamically get theme - always returns 'AppTheme' which is set in style
            try {
                PackageInfo packageInfo = getPackageMgr().getPackageInfo(GlobalInfo.s_globalInfo.pkgName, 0);
                 int themeResId = packageInfo.applicationInfo.theme;
                 String themeName = getResources().getResourceEntryName(themeResId);
                 themeTv.setText(themeName);
            } catch (Exception ex) {
                themeTv.setVisibility(View.GONE);
            }
        */

        Message msgObj = m_handler.obtainMessage(MSG_GET_UI_SIZE);
        m_handler.sendMessageDelayed(msgObj, 1000);

        final TextView touchPos = Ui.viewById(m_rootView, R.id.touch_pos);
        /*
        m_drawPoints = Ui.viewById(m_rootView, R.id.drawPoints);
        m_drawPoints.setOnTouchInfo(new DrawView.TouchInfo() {
            public void onTouchInfo(MotionEvent event) {
                touchPos.setText(String.format("%.0f,%.0f", event.getX(), event.getY()));
            }
        });
        */
    }
    /**
     *
     * @return Display Metrics.
     */
    DisplayMetrics getDisplayMetrics() {
        if (m_displayMetrics == null)
            m_displayMetrics = Ui.getDisplayMetrics(getContext());
        return m_displayMetrics;
    }

    /**
     * Layout has completed and now we can display the correct screen dimensions.
     */
    void setPanelSize() {
        int heightPx = m_vertPanelArrow.getHeight();
        float heightDp = Ui.pxToDp(heightPx);

        m_vertPanelText.setText(String.format("%d px\n%.0f dp\n%.1f in",
                heightPx, heightDp, (float) heightPx / getDisplayMetrics().densityDpi));

        int widthPx = m_horzWindowArrow.getWidth();
        float widthDp = Ui.pxToDp(widthPx);
        m_horzWindowText.setText(String.format("%d px|%.0f dp|%.1f in",
                widthPx, widthDp, (float) widthPx / getDisplayMetrics().densityDpi));
    }
}