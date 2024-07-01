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

import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.ALog.ALog;
import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;
import com.landenlabs.all_UiDemo.Util.Translation;

/**
 * Demonstrate grid layout of images.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="https://landenlabs.com/android"> author's web-site </a>
 */

public class GridImagesFrag  extends UiFragment   {

    private View mRootView;
    private static final  int dimPx = 85;
    private static final  long ANIM_MILLI = 2000;
    private static final  int incr = 200;
    private static Rect screenRect;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.grid_images, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.grid_images_id;
    }

    @Override
    public String getName() {
        return "FullScreen";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onResume() {
        super.onResume();
        Point pt = new Point();
        mRootView.getDisplay().getSize(pt);
        screenRect = new Rect(0, 0, pt.x, pt.y);
    }

    private void setup() {
        GridView gridview = Ui.needViewById(mRootView, R.id.gridview);
        gridview.setClipChildren(false);
        RadioGroup radioGroup = Ui.needViewById(mRootView, R.id.grid_image_rg);
        gridview.setAdapter(new ImageAdapter(getActivity(), radioGroup));
    }

    private static void scaleImage(View view, int id, int pos) {
        if (view instanceof  ImageView) {
            ImageView imgView = (ImageView)view;
            imgView.setImageResource(mThumbIds[pos]);
        }
        View rootView = view.getRootView();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        Transition autoTransition;

        if (id == R.id.grid_image_scale1) {
            view.setBackgroundColor(0x80ff0000);    // red
            params.width += incr;
            params.height += incr;
            view.setLayoutParams(params);
        } else if (id == R.id.grid_image_scale2) {
            if (false) {
                autoTransition = new AutoTransition();
                autoTransition.setDuration(ANIM_MILLI);
                TransitionManager.beginDelayedTransition((ViewGroup) rootView, autoTransition);
            } else {
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.setDuration(ANIM_MILLI);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        /*
                        https://medium.com/@andkulikov/animate-all-the-things-transitions-in-android-914af5477d50
                        ChangeBounds changeBounds = new ChangeBounds();
                        changeBounds.setPathMotion(new ArcMotion());
                        transitionSet.addTransition(changeBounds);
                        transitionSet.addTransition(new ChangeImageTransform());
                        transitionSet.addTransition(new ChangeTransform());
                        transitionSet.addTransition(new Slide());
                         */
                    transitionSet.addTransition(new AutoTransition());
                    transitionSet.addTransition(new Translation());
                }
                transitionSet.addTransition(new ChangeBounds());

                TransitionManager.beginDelayedTransition((ViewGroup) rootView, transitionSet);
            }
            view.setBackgroundColor(Color.WHITE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(params.width / 10f);
            }

            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setGravity(Gravity.RIGHT);
            }
            // view.setForegroundGravity(Gravity.RIGHT);

            params.width += incr;
            params.height += incr;
            // view.requestLayout();

            // Rect viewableRect = new Rect();
            // view.getGlobalVisibleRect(viewableRect);
            if (view.getX() < 0) {
                // view.setX(incr);
                view.setTranslationX(incr * 2);
                ViewGroup vg = (ViewGroup) view.getParent();
                vg.setClipChildren(false);
            } else if (view.getX() + params.width > screenRect.width()) {
                view.setX(screenRect.width() - params.width);
                // view.setTranslationX(-view.getX());
            }
            view.requestLayout();
            view.invalidate();
        } else if (id == R.id.grid_image_down) {
            if (params.width > incr) {
                view.setBackgroundColor(Color.WHITE);
                autoTransition = new AutoTransition();
                autoTransition.setDuration(ANIM_MILLI);
                TransitionManager.beginDelayedTransition((ViewGroup) rootView, autoTransition);
                params.width -= incr;
                params.height -= incr;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.setElevation(params.width / 10f);
                }
                view.requestLayout();
                view.invalidate();
            } else {
                scaleImage(view, R.id.grid_image_reset, pos);
            }
        } else if (id == R.id.grid_image_reset) {
            params.width = dimPx;
            params.height = dimPx;
            view.setLayoutParams(params);
            if (view instanceof ImageView) {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    // references to our images
    private static final Integer[] mThumbIds = {
            R.drawable.image_a, R.drawable.image_c,
            R.string.app_name, R.drawable.image_f,
            R.drawable.image_p, R.drawable.image_s,

            R.drawable.image100, R.drawable.image200,
            R.drawable.image400, R.drawable.image600,

            R.string.app_name, R.drawable.image_c,
            R.drawable.image_e, R.drawable.image_f,
            R.drawable.image_p, R.drawable.image_s,

            R.drawable.image_a, R.drawable.image_c,
            R.drawable.image_e,  R.string.app_name,
            R.drawable.image_p, R.drawable.image_s,

            R.drawable.image_a, R.drawable.image_c,
            R.drawable.image_e, R.drawable.image_f,
            R.drawable.image_p, R.drawable.image_s,
    };


    // =============================================================================================
    static class  ImageAdapter extends BaseAdapter {
        private final Context mContext;
        private final RadioGroup mRadioGroup;

        ImageAdapter(Context context, RadioGroup radioGroup) {
            mContext = context;
            mRadioGroup = radioGroup;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                // If it's not recycled, initialize some attributes
                try {
                    String text = mContext.getString(mThumbIds[position]);
                    if (text.contains("/drawable")) {
                        view = makeImage(position);
                    } else {
                        view = makeText(text, position);
                    }
                } catch (Resources.NotFoundException ex) {
                    view = makeImage(position);
                }

                if (Build.VERSION.SDK_INT >= 21) {
                    view.setStateListAnimator(AnimatorInflater.loadStateListAnimator(mContext, R.animator.press));
                }
            } else {
                view = convertView;
            }

            // Image must be set in background to ue stateList animation
            if (view instanceof  ImageView) {
                view.setBackgroundResource(mThumbIds[position]);
            }
            return view;
        }

        private TextView makeText(String text, final int pos) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(dimPx, dimPx));
            textView.setPadding(8, 8, 8, 8);
            textView.setText(text);
            textView.setOnClickListener(view -> doAction(view, pos));
            return textView;
        }

        private ImageView makeImage(final int pos) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(dimPx, dimPx));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

            imageView.setOnClickListener(view -> doAction(view, pos));

            return imageView;
        }

        private void doAction(View view, int pos) {
            Toast.makeText(view.getContext(), "Grid Pos:" + pos, Toast.LENGTH_SHORT).show();
            int ckId = mRadioGroup.getCheckedRadioButtonId();

            try {
                if (ckId == R.id.grid_image_statelist) {
                } else if (ckId == R.id.grid_image_brAnim) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (view instanceof ImageView) {
                            ((ImageView) view).setImageResource(mThumbIds[pos]);
                        }
                        view.setBackgroundResource(R.drawable.anim_grady1);
                        ((AnimatedVectorDrawable) view.getBackground()).start();
                    }
                } else if (ckId == R.id.grid_image_elev) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // view.setTranslationZ(20.0f);
                        // view.setZ(20.0f);
                        view.setElevation(20.0f);
                    }
                } else if (ckId == R.id.grid_image_scale1 || ckId == R.id.grid_image_scale2 || ckId == R.id.grid_image_down || ckId == R.id.grid_image_reset) {
                    scaleImage(view, ckId, pos);
                }
            } catch (Exception ex) {
                ALog.w.msg(ex.getMessage());
            }
        }
    }

}
