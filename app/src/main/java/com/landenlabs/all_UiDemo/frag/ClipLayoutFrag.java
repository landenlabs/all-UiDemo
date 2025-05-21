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

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Seek.SeekBarWithLabel;
import com.landenlabs.all_UiDemo.Util.ClipPathLinearLayout;
import com.landenlabs.all_UiDemo.Util.ClipPathPorterLinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrate clipping overlapping widgets
 * <p>
 * https://medium.com/appkode/clipping-in-android-quickly-qualitatively-cheap-3ccfd31d5d6b
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://LanDenLabs.com/android"> author's web-site </a>
 */

public class ClipLayoutFrag extends UiFragment {

    private View mRootView;
    private ViewGroup mHolder1;
    private ClipPathLinearLayout mHolder2;
    private ClipPathPorterLinearLayout mHolder3;
    private TextView mText1;
    private RadioGroup mModeRg;
    private SeekBarWithLabel mSeekBar1;
    private TextView mText2;
    private Spinner mSpinner1;
    protected List<String> options;

    // ---------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.clip_layout, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.clip_layout_id;
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

        //
        // https://medium.com/appkode/clipping-in-android-quickly-qualitatively-cheap-3ccfd31d5d6b
        //

        mHolder1 = mRootView.findViewById(R.id.clip_holder1);
        mHolder2 = mRootView.findViewById(R.id.clip_holder2);
        mHolder3 = mRootView.findViewById(R.id.clip_holder3);
        mText1 = mRootView.findViewById(R.id.clip_text1);
        mText2 = mRootView.findViewById(R.id.clip_text2);
        mModeRg = mRootView.findViewById(R.id.clip_mode_holder);
        mSeekBar1 = mRootView.findViewById(R.id.clip_seekBar1);

        mSeekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 int radius = mHolder2.getWidth()/2 * progress/100;
                 mHolder2.setRadius(radius, radius/2);
                 mHolder3.setRadius(radius, radius/2);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {
             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {
             }
         });

        mModeRg.setOnCheckedChangeListener((group, checkedId) -> {
            setOutline();
            mHolder1.invalidate();
        });

        initSpinner1();
        setOutline();

        mSeekBar1.post(() -> mSeekBar1.setProgress(50));
    }

    private void initSpinner1() {
        mSpinner1 = mRootView.findViewById(R.id.clip_spinner1);

        options = new ArrayList<>();
        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
            options.add(mode.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter);
        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = options.get(position);
                // option.drawIt(imageView);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        mSpinner1.setSelection(1);
    }

    private void setOutline() {

        mHolder1.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                Rect foo = mHolder1.getClipBounds();
                mHolder1.setClipToOutline(true);
                int marg = 40;
                int marg2 = marg*2;
                Rect rect = new Rect(marg2, marg2, view.getWidth() - marg, view.getHeight() -  marg);
                RectF rectf = new RectF(rect);

                int checkedRadioButtonId = mModeRg.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.clip_mode1) {// Clip (mask) using Rectangle
                    outline.setRect(marg2, marg2, view.getWidth() - marg, view.getHeight() - marg);
                } else if (checkedRadioButtonId == R.id.clip_mode2) {// Clip (mask) using round Rectangle
                    outline.setRoundRect(rect, marg2);
                } else if (checkedRadioButtonId == R.id.clip_mode3) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Path path = new Path();
                        path.addOval(rectf, Path.Direction.CW);
                        //path.addRect(rectf, Path.Direction.CW);
                        outline.setPath(path);  // does not work
                    }
                } else if (checkedRadioButtonId == R.id.clip_mode4) {
                    mHolder1.setClipToOutline(false);
                }
            }

        });
    }

    private void setTintMode() {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled},
                new int[]{android.R.attr.state_active},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_pressed},
                new int[]{}
        };
        ColorStateList colorStateList = new ColorStateList(states,
                new int[]{0xff00ff00, 0xff00ff00, 0xff00ff00, 0xff00ff00, 0xff00ff00, 0xff00ff00 });

        mText1.setBackgroundColor(Color.WHITE);
        mText1.setBackgroundTintList(colorStateList);
        mText1.setBackgroundTintMode(PorterDuff.Mode.SRC);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // mText1.setBackgroundTintBlendMode(BlendMode.CLEAR);
        }
        mText2.setBackgroundTintList(colorStateList);
        mText2.setBackgroundTintMode(PorterDuff.Mode.MULTIPLY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // mText2.setBackgroundTintBlendMode(BlendMode.CLEAR);
        }
    }

}
