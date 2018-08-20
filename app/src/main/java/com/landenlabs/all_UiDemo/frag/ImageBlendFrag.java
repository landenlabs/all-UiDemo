package com.landenlabs.all_UiDemo.frag;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

/**
 * Demonstrate Image Blend using PorterDuff modes.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */

public class ImageBlendFrag  extends UiFragment implements View.OnClickListener  {

    private View mRootView;
    private ViewGroup mImageHolder;
    private int mColor = Color.RED;
    private final int mSrcImg = R.drawable.image100;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.image_blend, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.image_blend_id;
    }

    @Override
    public String getName() {
        return "ImageBlend";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.blend_red:
                mColor = Color.RED;
                setImage(mSrcImg);
                break;
            case R.id.blend_green:
                mColor = Color.GREEN;
                setImage(mSrcImg);
                break;
            case R.id.blend_blue:
                mColor = Color.BLUE;
                setImage(mSrcImg);
                break;
            case R.id.blend_img1:
                mColor = 0;
                setImage(mSrcImg, R.drawable.checkmark5);
                break;
            case R.id.blend_img2:
                mColor = 0;
                setImage(mSrcImg, R.drawable.image_a);
                break;
            case R.id.blend_img3:
                mColor = 0;
                setImage(mSrcImg, R.drawable.image_e);
                break;
            case R.id.blend_img4:
                mColor = 0;
                setImage(mSrcImg, R.drawable.uidemo_sm);
                break;
        }
    }

    private void setup() {
        mImageHolder = Ui.viewById(mRootView, R.id.image_holder);

        Ui.viewById(mRootView, R.id.blend_red).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.blend_green).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.blend_blue).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.blend_img1).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.blend_img2).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.blend_img3).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.blend_img4).setOnClickListener(this);

        mImageHolder.removeAllViews();
        addImages();
        setImage(mSrcImg, R.drawable.uidemo_sm);
    }

    private void setImage(int imageRes) {
        for (int idx = 0; idx < mImageHolder.getChildCount(); idx++) {
            View view = mImageHolder.getChildAt(idx);
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView)view;
                imageView.setImageResource(imageRes);
                PorterDuff.Mode mode = (PorterDuff.Mode)imageView.getTag();
                imageView.setColorFilter(mColor, mode);
            }
        }
    }

    private void setImage(int imageSrcRes, int imageDstRes) {
        for (int idx = 0; idx < mImageHolder.getChildCount(); idx++) {
            View view = mImageHolder.getChildAt(idx);
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView)view;
                imageView.setColorFilter(null);

                Bitmap srcBm = BitmapFactory.decodeResource(getResources(), imageSrcRes);
                Bitmap dstBm = BitmapFactory.decodeResource(getResources(), imageDstRes);
                Bitmap resultBm = Bitmap.createBitmap(srcBm.getWidth(), srcBm.getHeight(), Bitmap.Config.ARGB_8888);

                Canvas resultCanvas = new Canvas(resultBm);
                Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
                Rect srcRect = new Rect(0, 0, srcBm.getWidth(), srcBm.getHeight());
                resultCanvas.drawBitmap(srcBm, 0, 0, paint);

                if (imageView.getTag() != null) {
                    PorterDuff.Mode mode = (PorterDuff.Mode) imageView.getTag();
                    paint.setXfermode(new PorterDuffXfermode(mode));

                    Rect dstRect = new Rect(0, 0, dstBm.getWidth(), dstBm.getHeight());
                    resultCanvas.drawBitmap(dstBm, dstRect, srcRect, paint);

                    imageView.setImageBitmap(resultBm);
                } // else {
                 //  imageView.setImageBitmap(srcBm);
                // }
            }
        }
    }

    private void addImages() {
        addImage(PorterDuff.Mode.SRC);
        addImage(PorterDuff.Mode.DST);
        addImage(PorterDuff.Mode.SRC_IN);
        addImage(PorterDuff.Mode.DST_IN);
        addImage(PorterDuff.Mode.SRC_OUT);
        addImage(PorterDuff.Mode.DST_OUT);
        addImage(PorterDuff.Mode.SRC_ATOP);
        addImage(PorterDuff.Mode.DST_ATOP);
        addImage(PorterDuff.Mode.SRC_OVER);
        addImage(PorterDuff.Mode.DST_OVER);
        addImage(PorterDuff.Mode.ADD);
        addImage(PorterDuff.Mode.DARKEN);
        addImage(PorterDuff.Mode.LIGHTEN);
        addImage(PorterDuff.Mode.MULTIPLY);
        addImage(PorterDuff.Mode.SCREEN);
        addImage(PorterDuff.Mode.XOR);

        addBlend(0xff404040, PorterDuff.Mode.MULTIPLY);
        addBlend(0xff808080, PorterDuff.Mode.MULTIPLY);
        addBlend(0xffc0c0c0, PorterDuff.Mode.MULTIPLY);

        addBlend(0x40ffffff, PorterDuff.Mode.MULTIPLY);
        addBlend(0x80ffffff, PorterDuff.Mode.MULTIPLY);
        addBlend(0xc0ffffff, PorterDuff.Mode.MULTIPLY);
    }

    private void addImage(PorterDuff.Mode mode) {
        TextView tv = new TextView(mImageHolder.getContext());
        tv.setBackgroundResource(R.color.rowtx);
        tv.setGravity(Gravity.CENTER);
        // tv.setTextAppearance(R.style.TextAppearanceWhite20);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        tv.setTextColor(Color.WHITE);
        tv.setText(mode.toString());
        tv.setPadding(5,5,5,5);
        mImageHolder.addView(tv);
        ImageView iv = new ImageView(mImageHolder.getContext());
        iv.setBackgroundResource(R.color.row0);
        mImageHolder.addView(iv);
        iv.setColorFilter(mColor, mode);
        iv.setTag(mode);
    }

    @SuppressWarnings("SameParameterValue")
    private void addBlend(int blendColor, PorterDuff.Mode mode) {
        try {
            TextView tv = new TextView(mImageHolder.getContext());
            tv.setBackgroundResource(R.color.rowtx);
            tv.setGravity(Gravity.CENTER);
            // tv.setTextAppearance(R.style.TextAppearanceWhite20);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            tv.setTextColor(Color.WHITE);
            tv.setText(String.format("Tint %X ", blendColor) + mode.toString());
            tv.setPadding(5, 5, 5, 5);
            mImageHolder.addView(tv);
            ImageView iv = new ImageView(mImageHolder.getContext());
            iv.setBackgroundResource(R.color.row0);
            iv.setImageResource(R.drawable.image100);
         //   iv.setColorFilter(mColor, mode);
         //   iv.setTag(mode);
            mImageHolder.addView(iv);

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{ new int[]{}},
                    new int[]{  blendColor}
            );

            if (Build.VERSION.SDK_INT >= 21) {
                iv.getDrawable().setTintList(colorStateList);
                iv.getDrawable().setTintMode(mode);
            } else {
                Drawable drawable = DrawableCompat.wrap(iv.getDrawable());
                DrawableCompat.setTintList(drawable.mutate(), colorStateList);
                DrawableCompat.setTintMode(drawable.mutate(), mode);
            }
            // iv.setTag(null);
        } catch (Exception ex) {
            Log.e("ImageBlendFrag", ex.getMessage());
            throw ex;
        }
    }
}
