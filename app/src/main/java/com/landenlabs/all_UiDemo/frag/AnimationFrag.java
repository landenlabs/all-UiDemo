package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

@SuppressWarnings("FieldCanBeLocal")
public class AnimationFrag  extends UiFragment implements View.OnClickListener {

    private View mRootView;
    private TextView mStatus;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    private Animation loadAnimation(int id) {
        Animation animation = AnimationUtils.loadAnimation(getActivity(), id);
        animation.setFillAfter(true);
        return  animation;
    }

    @Override
    public void onClick(View view) {
        // ToggleButton tb = (view instanceof ToggleButton) ? (ToggleButton)view : null;
        // final View target = (view.getTag() instanceof View) ? (View)view.getTag() : null;

        ToggleButton tb = cast2(ToggleButton.class, view);
        final View target =  cast2(View.class, view.getTag());

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
                // AlphaAnimation does not change the view's alpha it changes the transformation alpha.
                // The view  final alpha = view.alpha * transformation.alpha
                // NOTE - If view's alpha is zero, then no fade occurs because
                //               final alpha = 0 * transformation.alpha is zero.
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

            case R.id.animation_scaleX:
                if (tb.isChecked()) {
                    target.animate().scaleX(0).setDuration(2000).start();
                } else {
                    target.animate().scaleX(1).setDuration(2000).start();
                }
                break;


            case R.id.animation_translateX:
                if (tb.isChecked()) {
                    int w = target.getWidth();
                    target.animate().translationX(w).setDuration(2000).start();
                } else {
                    target.animate().translationX(0).setDuration(2000).start();
                }
                break;

            case R.id.animation_slideR:
                if (tb.isChecked()) {
                    int w = target.getWidth();
                    target.animate().scaleX(0).translationX(w/2).setDuration(2000).start();
                } else {
                    target.animate().scaleX(1).translationX(0).setDuration(2000).start();
                }
                break;


            case R.id.animation_slideL:
                if (tb.isChecked()) {
                    int w = target.getWidth();
                    target.animate().scaleX(0).translationX(-w/2).setDuration(2000).start();
                } else {
                    target.animate().scaleX(1).translationX(0).setDuration(2000).start();
                }
                break;

            case R.id.animation_rot360:
                if (tb.isChecked()) {
                    target.animate().rotationBy(360).setDuration(2000).start();
                } else {
                    target.animate().rotationBy(-360).setDuration(2000).start();
                }
                break;
            case R.id.animation_rot180:
                if (tb.isChecked()) {
                    target.animate().rotationBy(180).setDuration(2000).start();
                } else {
                    target.animate().rotationBy(-180).setDuration(2000).start();
                }
                break;

            case R.id.animation_rot90:
                if (tb.isChecked()) {
                    target.animate().rotationBy(90).setDuration(2000).start();
                } else {
                    target.animate().rotationBy(-90).setDuration(2000).start();
                }
                break;

            case R.id.animation_rot90x:
                if (tb.isChecked()) {
                    target.animate().rotationXBy(90).setDuration(2000).start();
                } else {
                    target.animate().rotationXBy(-90).setDuration(2000).start();
                }
                break;


            case R.id.animation_rot90y:
                if (tb.isChecked()) {
                    target.animate().rotationYBy(90).setDuration(2000).start();
                } else {
                    target.animate().rotationYBy(-90).setDuration(2000).start();
                }
                break;

            case R.id.get_alpha:
                getAlpha();
                break;
        }
    }

    private void showAnimStatus(final View target) {
        if (target != null) {
            final TextView status = (TextView)(target.getTag());
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
                // Note the withEndAction() is only called once then removed from the animate.
                // Use animate().setUpdateListener(...) for permanent callback.
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

    @SuppressWarnings("unchecked")
    private <T extends View, RR>  T cast2(Class<RR> clazz, Object obj) {
        T result = null;

        if (obj != null && clazz.isAssignableFrom(obj.getClass())) {
            result =  (T) obj;
        }
        return result;
    }

    private void getAlpha() {
        GridLayout gridLayout = Ui.viewById(mRootView, R.id.animation_grid);

        int colCnt = gridLayout.getColumnCount();
        int childCnt = gridLayout.getChildCount();

        for (int idx = 0; idx < childCnt; idx += colCnt) {
            View child = gridLayout.getChildAt(idx);
            View target = gridLayout.getChildAt(idx+1);
            TextView status = cast2(TextView.class, gridLayout.getChildAt(idx+2));

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
