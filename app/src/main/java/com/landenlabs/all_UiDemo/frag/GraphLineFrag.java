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

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.ALog.ALog;
import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

import java.util.ArrayList;


// import javax.microedition.khronos.opengles.GL10;


/**
 * Demonstrate Scrollview Line Graph (chart).
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class GraphLineFrag  extends UiFragment implements View.OnClickListener {

    private View        mRootView;
    private ViewGroup   mGraphView;


    // =============================================================================================
    public static class LineView extends View {

        float mXScale = 5;
        float mLineWidth = 2;
        int   mColor = Color.RED;
        boolean mAddShadow = false;

        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final Path path = new Path();
        private final ArrayList<Float> mPoints = new ArrayList<>();
        private Shader shader;

        public LineView(Context context) {
            super(context);
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
        }

        public void enableShadow(boolean enable) {
            mAddShadow = enable;
            // paint.setStyle(Paint.Style.FILL_AND_STROKE);
            // paint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            paint.setColor(mColor);
            paint.setStrokeWidth(mLineWidth);

            if (!mPoints.isEmpty()) {
                path.reset();
                path.moveTo(0, mPoints.get(0));
                for (int idx = 1; idx < mPoints.size(); idx++) {
                    path.lineTo(idx * mXScale, mPoints.get(idx));
                }

                if (mAddShadow) {
                    paint.setShader(null);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawPath(path, paint);

                    // Close graph.
                    path.lineTo((mPoints.size()-1) * mXScale, getHeight());
                    path.lineTo(0, getHeight());
                    path.close();

                    if (shader == null) {
                        shader = new LinearGradient(
                                0, 0,
                                0, getHeight(),
                                Color.WHITE,0,
                                Shader.TileMode.CLAMP);
                    }

                    paint.setShader(shader);
                    paint.setStyle(Paint.Style.FILL);   // Paint.Style.FILL_AND_STROKE);

                    // path.setFillType(Path.FillType.WINDING);
                    // path.setFillType(Path.FillType.INVERSE_WINDING);
                    // path.setFillType(Path.FillType.EVEN_ODD);
                    // path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
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
        //noinspection SwitchStatementWithTooFewBranches
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
                        400);

        mGraphView.removeAllViews();

        final int updateMill = 50;
        final int maxPoints = 100;
        final int maxRange = 400;
        try {
            //noinspection PointlessArithmeticExpression
            mLineView1 = createLine(mGraphView, lp, 0xffff4040, 2, updateMill*1, maxPoints, maxRange/2);
            int lineScale2 = 4;
            mLineView2 = createLine(mGraphView, lp, 0xff40ff40, 4, updateMill*2, maxPoints/lineScale2, maxRange);
            mLineView2.enableShadow(true);
            mLineView2.mXScale *= lineScale2;

        } catch (Exception ex) {
            ALog.e.tagMsg(this, ex);
        }

        // Ui.viewById(mRootView, R.id.scroll_add).setOnClickListener(this);
    }

    private LineView createLine(
            ViewGroup viewGroup, FrameLayout.LayoutParams lp, int lineColor, int lineWidth,
            final int updateMill, final int maxPoints, final int maxRange) {

        final LineView lineView = new LineView(mGraphView.getContext());
        lineView.mColor = lineColor;
        lineView.mLineWidth = lineWidth;
        // lineView.setBackgroundColor(0);
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
