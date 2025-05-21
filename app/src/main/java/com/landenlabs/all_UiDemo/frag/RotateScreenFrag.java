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

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;

/**
 * Demonstrate forcing screen to rotate
 * <p>
 * https://developer.android.com/reference/android/content/pm/ActivityInfo
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://LanDenLabs.com/android"> author's web-site </a>
 */

public class RotateScreenFrag  extends UiFragment implements View.OnClickListener {

    private View mRootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.rotatescreen_frag, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.rotate_screen_id;
    }

    @Override
    public String getName() {
        return "Rotate Screen";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        // https://developer.android.com/reference/android/content/pm/ActivityInfo
        mRootView.findViewById(R.id.rot_unspecified).setOnClickListener(this);
        mRootView.findViewById(R.id.rot_portrait).setOnClickListener(this);
        mRootView.findViewById(R.id.rot_portrait_reverse).setOnClickListener(this);
        mRootView.findViewById(R.id.rot_landscape).setOnClickListener(this);
        mRootView.findViewById(R.id.rot_landscape_reverse).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // https://developer.android.com/reference/android/content/pm/ActivityInfo
        int id = view.getId();
        if (id == R.id.rot_unspecified) {
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else if (id == R.id.rot_portrait) {
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (id == R.id.rot_portrait_reverse) {
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        } else if (id == R.id.rot_landscape) {
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (id == R.id.rot_landscape_reverse) {
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        }

    }

    //
}
