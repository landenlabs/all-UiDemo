package com.landenlabs.all_UiDemo.Seek;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.landenlabs.all_UiDemo.Ui;
import com.landenlabs.all_UiDemo.frag.SeekbarVertFrag;

public class SeekBarWithLabelHorizontal {

    public interface Apply {
        void apply(SeekBar seekbar, int progress);
    };

    public SeekBarWithLabelHorizontal(View rootView, int seekBarRes, int seekBarLbl,  Apply apply) {
        final TextView seekLbl =  Ui.viewById(rootView, seekBarLbl);
        SeekBar seekBar =  Ui.viewById(rootView, seekBarRes);

        if (seekLbl.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)seekLbl.getParent();
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                float thumb_x = (float)progress * seekBar.getWidth() / seekBar.getMax();
                float thumb_y = 0;
                String text = String.valueOf(progress);
                Rect txtBounds = new Rect();
                seekLbl.setText(text);

                seekLbl.getPaint().getTextBounds(text, 0, text.length(), txtBounds);

                seekLbl.setX(seekBar.getLeft() + thumb_x - txtBounds.width()/2f);
                seekLbl.setY(seekBar.getTop() + thumb_y - Math.max(seekLbl.getHeight(), txtBounds.height()));

                // Does not work
                // seekLbl.setLeft(Math.round(seekBar.getLeft() + thumb_x - txtBounds.width()/2f));
                // seekLbl.setTop(Math.round(seekBar.getTop() + thumb_y - txtBounds.height()));
                // seekLbl.invalidate();

                seekBar.setSecondaryProgress(progress/2);
                apply.apply(seekBar, progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }});
    }
}