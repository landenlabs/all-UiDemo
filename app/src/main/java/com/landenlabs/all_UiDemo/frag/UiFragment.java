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

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.landenlabs.all_UiDemo.ALog.ALog;

import java.util.Objects;

/**
 * Base page fragment for UI Demo app.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://LanDenLabs.com/android"> author's web-site </a>
 */
public abstract class UiFragment extends Fragment {

    protected abstract int getFragId();
    protected abstract String getName();
    public abstract String getDescription();


    @NonNull
    Context getContextSafe() {
        return requireContext();
    }
    @NonNull
    private Activity getActivitySafe() {
        return requireActivity();
    }

    public Window getWindowSafe() {
        return Objects.requireNonNull(getActivitySafe().getWindow());
    }

    @NonNull
    private FragmentManager getFragmentMgrSafe() {
        return requireFragmentManager();
    }

    @NonNull
    <T> T getServiceSafe(String service) {
        //noinspection unchecked
        return (T)Objects.requireNonNull(getActivitySafe().getSystemService(service));
    }

    @SuppressWarnings("unused")
    @NonNull
    Window getWindow() {
        return Objects.requireNonNull(getActivitySafe().getWindow());
    }


    @Override
    public void onDestroyView()
    {
        ALog.d.tagMsg(this,  "onDestroyView id=" ,  getName() ,  " #" ,  getFragId());

        super.onDestroyView();

        if (! getRetainInstance()) {
            // Required to prevent duplicate id when Fragment re-created.
            int fragId = getFragId();
            Fragment fragment = getFragmentMgrSafe().findFragmentById(fragId);
            if (fragment != null) {
                FragmentTransaction ft = getFragmentMgrSafe().beginTransaction();
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }
    }

    Resources.Theme getTheme() {
        return getContextSafe().getTheme();
    }

    Drawable getDrawable(int resId) {
        return ResourcesCompat.getDrawable(getResources(), resId, getTheme());
    }
}
