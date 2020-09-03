package com.landenlabs.all_UiDemo.frag;

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

import android.graphics.Rect;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Seek.SeekBarWithLabelHorizontal;
import com.landenlabs.all_UiDemo.Ui;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Demonstrate ripple on various views using different settings.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */
public class SeekbarVertFrag extends UiFragment
        implements  SeekBarWithLabelHorizontal.Apply {

    // ---- Local Data ----
    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.seekbar_vert_frag, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.seekbar_vert_id;
    }

    @Override
    public String getName() {
        return "SeekbarVt";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    SeekBarWithLabelHorizontal primaryBar;
    SeekBarWithLabelHorizontal secondaryBar;

    ArrayList<ProgressBar>  bars = new ArrayList<>();

    private void setup() {
        primaryBar = new SeekBarWithLabelHorizontal(mRootView, R.id.seekBarH1, R.id.seekBarH1_Lbl, this);
        secondaryBar = new SeekBarWithLabelHorizontal(mRootView, R.id.seekBarH2, R.id.seekBarH2_Lbl, this);

        bars.add( Ui.viewById(mRootView, R.id.seekBarV2));
        bars.add( Ui.viewById(mRootView, R.id.progressBarV1));
        bars.add(  Ui.viewById(mRootView, R.id.progressBarV2));
        bars.add(  Ui.viewById(mRootView, R.id.progressBarV3));
    }

    @Override
    public void apply(SeekBar seekbar, int progress) {

        if (seekbar.getId() == R.id.seekBarH1) {
            for (ProgressBar bar : bars) {
                bar.setProgress(progress);
            }
        } else {
            for (ProgressBar bar : bars) {
                bar.setSecondaryProgress(progress);
            }
        }
    }
}
