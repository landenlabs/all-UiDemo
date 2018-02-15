
/**
 * Copyright (c) 2015 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
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
 * @author Dennis Lang  (3/21/2015)
 * @see http://landenlabs.com
 *
 */


package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate animations
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */

public class AnimationFrag  extends UiFragment implements View.OnClickListener {

    View mRootView;
    TextView mStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.animation, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.animation_id;
    }

    @Override
    public String getName() {
        return "Animation";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        Ui.viewById(mRootView, R.id.get_alpha).setOnClickListener(this);
        mStatus = Ui.viewById(mRootView, R.id.show_alpha);
        GridLayout gridLayout = Ui.viewById(mRootView, R.id.animation_grid);


        int colCnt = gridLayout.getColumnCount();
        int childCnt = gridLayout.getChildCount();

        for (int idx = 0; idx < childCnt; idx += colCnt) {
            View child = gridLayout.getChildAt(idx);
            child.setOnClickListener(this);
            View target = gridLayout.getChildAt(idx+1);
            child.setTag(target );
            View status = gridLayout.getChildAt(idx+2);
            target.setTag(status);
        }
    }

    private  <E extends View> E cast(Object view) {
        E e = null;
        try {
            e = (E)view;
        } catch (Exception ex) {
            e = null;
        }
        return e;
    }

    private Animation loadAnimation(int id) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), id);
        animation.setFillAfter(true);
        return  animation;
    }

    @Override
    public void onClick(View view) {
        ToggleButton tb = (view instanceof ToggleButton) ? (ToggleButton)view : null;
        final View target = cast(view.getTag());

        switch (view.getId()) {
            case R.id.animation_fade1:
                showAnimStatus(target); // Can wire up before animate set.
                // Changes  view alpha directly.
                if (tb.isChecked()) {
                    target.animate().alpha(0).setDuration(2000).start();
                } else {
                    target.animate().alpha(1).setDuration(2000).start();
                }
                break;

            case R.id.animation_fade2:
                // View alpha does not change, transformation alpha changes and
                // view will use final alpha = view.alpha * transformation.alpha
                // NOTE - If view's alpha is zero, then no fade occur because
                //               final alpha = 0 * transfromation.alpha is zero.
                //
                // See AlphaAnimation alphaAnimation
                //
                if (tb.isChecked()) {
                    target.setAnimation(loadAnimation(R.anim.fad_out));
                } else {
                    target.setAnimation(loadAnimation(R.anim.fad_in));
                }
                showAnimStatus(target); // wire up status after animation set.
                target.startAnimation(target.getAnimation());
                break;

            case R.id.get_alpha:
                getAlpha();
                break;
        }
    }

    private void showAnimStatus(final View target) {
        if (target != null) {
            final TextView status = cast(target.getTag());
            if (target.getAnimation() != null) {
                target.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        status.setText("Animation Start a=" + target.getAlpha());
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        status.setText("Animation End a=" + target.getAlpha());
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        status.setText("Animation Repeat");
                    }
                });
            } else if (target.animate() != null) {
                status.setText("animate Start a=" + target.getAlpha());
                target.animate().withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        status.setText("animate End a=" + target.getAlpha());
                    }
                });
            } else {
                status.setText("No animation");
            }
        }
    }

    private void getAlpha() {
        GridLayout gridLayout = Ui.viewById(mRootView, R.id.animation_grid);

        int colCnt = gridLayout.getColumnCount();
        int childCnt = gridLayout.getChildCount();

        for (int idx = 0; idx < childCnt; idx += colCnt) {
            View child = gridLayout.getChildAt(idx);
            View target = gridLayout.getChildAt(idx+1);
            TextView status = cast(gridLayout.getChildAt(idx+2));

            status.setText("Alpha=" + target.getAlpha());
            if (target.getAnimation() != null) {
                Transformation transformation = new Transformation();
                target.getAnimation().getTransformation(System.currentTimeMillis(), transformation);
                status.setText(status.getText().toString() + "\n TransAlpha=" +
                        String.format("%.2f", transformation.getAlpha()));
            }
        }
    }
}
