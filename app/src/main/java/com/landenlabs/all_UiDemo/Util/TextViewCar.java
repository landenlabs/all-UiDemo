/*
 * IBM Confidential
 * Copyright IBM Corp. 2016, 2021. Copyright WSI Corporation 1998, 2015
 */

package com.landenlabs.all_UiDemo.Util;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom variant of TextView to support easy global restriction of Text Length.
 */
public class TextViewCar extends androidx.appcompat.widget.AppCompatTextView {

    // Debug diagnostic counters to track possible memory leaks.
    public final static AtomicInteger cntTextViewCar  = new AtomicInteger(0);
    public final static AtomicInteger cntRegister  = new AtomicInteger(0);
    public final static AtomicInteger cntUnregister  = new AtomicInteger(0);

    public TextViewCar(Context context) {
        super(context);
        cntTextViewCar.incrementAndGet();   // Debug diagnostic counters to track possible memory leaks.
    }

    public TextViewCar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        cntTextViewCar.incrementAndGet();   // Debug diagnostic counters to track possible memory leaks.
    }

    public TextViewCar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        cntTextViewCar.incrementAndGet();   // Debug diagnostic counters to track possible memory leaks.
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        cntTextViewCar.decrementAndGet();   // Debug diagnostic counters to track possible memory leaks.
    }

    // ---------------------------------------------------------------------------------------------
    // Customize to support restricted length in Automotive apps.

    private CharSequence fullText;
    private BufferType type;
    private static int restrictedLength = Integer.MAX_VALUE;

    @Override
    public void setText(CharSequence fullText, BufferType type) {
        this.fullText = fullText;
        this.type = type;
        refresh();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        register(this);
        cntRegister.incrementAndGet();
    }

    @Override
    protected void onDetachedFromWindow() {
        unregister(this);
        cntUnregister.incrementAndGet();
        this.setOnClickListener(null);
        this.setOnTouchListener(null);
        super.onDetachedFromWindow();
    }

    private void refresh() {
        if (fullText.length() > restrictedLength) {
            super.setText(fullText.subSequence(0, restrictedLength), type);
        } else {
            super.setText(fullText, type);
        }
    }

    public static void setGlobalRestrictedLength(int restrictedLength) {
        synchronized (TEXT_VIEW_CAR_SET) {
           TextViewCar.restrictedLength = restrictedLength;
           for (TextViewCar textViewCar : TEXT_VIEW_CAR_SET) {
               textViewCar.refresh();
           }
        }
    }
    public static void register(@NonNull TextViewCar textViewCar) {
        synchronized (TEXT_VIEW_CAR_SET) {
            TEXT_VIEW_CAR_SET.add(textViewCar);
        }
    }
    public static void unregister(@NonNull TextViewCar textViewCar) {
        synchronized (TEXT_VIEW_CAR_SET) {
            TEXT_VIEW_CAR_SET.remove(textViewCar);
        }
    }
    private static final Set<TextViewCar> TEXT_VIEW_CAR_SET = new HashSet<>();
}
