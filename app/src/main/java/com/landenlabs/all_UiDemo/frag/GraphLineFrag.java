package com.landenlabs.all_UiDemo.frag;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

import java.util.ArrayList;


// import javax.microedition.khronos.opengles.GL10;


/**
 * Demonstrate Scrollview resizing with views above and below.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class GraphLineFrag  extends UiFragment implements View.OnClickListener {

    private View        mRootView;
    private ViewGroup   mGraphView;


    // =============================================================================================
    public static class LineView extends View {

        final float mXScale = 5;
        float mLineWidth = 2;
        int   mColor = Color.RED;

        private final Paint paint = new Paint();
        private final Path path = new Path();
        private final ArrayList<Float> mPoints = new ArrayList<>();

        public LineView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            paint.setColor(mColor);
            paint.setStrokeWidth(mLineWidth);
            paint.setStyle(Paint.Style.STROKE);

            if (!mPoints.isEmpty()) {
                path.moveTo(0, mPoints.get(0));
                for (int idx = 1; idx < mPoints.size(); idx++) {
                    path.lineTo(idx * mXScale, mPoints.get(idx));
                }

                canvas.drawPath(path, paint);
            }
        }
    }

    // =============================================================================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.graphline_frag, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.graphline_id;
    }

    @Override
    public String getName() {
        return "GraphLine";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            default:
                break;
        }
    }

    private LineView mLineView1;
    private LineView mLineView2;
    private Runnable mAddPoints;

    private void setup() {
        mGraphView = Ui.viewById(mRootView, R.id.graph_line_surfaceview);

        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);

        mGraphView.removeAllViews();

        final int updateMill = 50;
        final int maxPoints = 100;
        final int maxRange = 400;
        try {
            //noinspection PointlessArithmeticExpression
            mLineView1 = createLine(mGraphView, lp, 0xff801010, 2, updateMill*1, maxPoints, maxRange/2);
            mLineView2 = createLine(mGraphView, lp, 0xff108010, 4, updateMill*2, maxPoints, maxRange);
        } catch (Exception ex) {
            Log.e("GraphLineFrag", ex.getLocalizedMessage(), ex);
        }

        // Ui.viewById(mRootView, R.id.scroll_add).setOnClickListener(this);
    }

    private LineView createLine(
            ViewGroup viewGroup, FrameLayout.LayoutParams lp, int lineColor, int lineWidth,
            final int updateMill, final int maxPoints, final int maxRange) {

        final LineView lineView = new LineView(mGraphView.getContext());
        lineView.mColor = lineColor;
        lineView.mLineWidth = lineWidth;
        lineView.setBackgroundColor(0);
        viewGroup.addView(lineView, lp);

        mAddPoints = new Runnable() {
            @Override
            public void run() {
                if (lineView.mPoints.size() > maxPoints) {
                    lineView.mPoints.remove(0);
                }
                lineView.mPoints.add((float)(Math.random()*maxRange));
                lineView.invalidate();
                lineView.postDelayed(this, updateMill);
            }
        };
        lineView.postDelayed(mAddPoints, updateMill);

        return lineView;
    }
}
