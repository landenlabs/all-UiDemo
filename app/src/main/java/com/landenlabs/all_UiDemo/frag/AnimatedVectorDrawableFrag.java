package com.landenlabs.all_UiDemo.frag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Created by Dennis Lang on 10/5/17.
 */

public class AnimatedVectorDrawableFrag
        extends UiFragment
        implements View.OnClickListener{

        View mRootView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.animated_vector_drawable_frag, container, false);

            setup();
            return mRootView;
        }

        @Override
        public int getFragId() {
            return R.id.anim_vector_drawable_id;
        }

        @Override
        public String getName() {
            return "AnimVecDraw";
        }

        @Override
        public String getDescription() {
            return "??";
        }

    @Override
    public void onClick(View view) {
        if (view instanceof CompoundButton) {
            // The CompoundButton objects, such as CheckBox and Toggle will auto toggle their state
            Log.d("fxx", "a checked=" + ((CompoundButton) view).isChecked() + " selected=" + view.isSelected());
        } else {
            // Most views will not automatically change their state on click.
            if (view instanceof Checkable) {
                ((Checkable) view).toggle();
                view.setSelected(((Checkable) view).isChecked());
                Log.d("fxx", "b checked=" + ((Checkable) view).isChecked() + " selected=" + view.isSelected());
            } else {
                view.setSelected(!view.isSelected());
                Log.d("fxx", "b selected=" + view.isSelected());
            }
        }
    }


    private void setup() {
        Ui.viewById(mRootView, R.id.avd_check1).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.avd_check2).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.avd_check3).setOnClickListener(this);
    }

}
