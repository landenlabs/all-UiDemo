package com.landenlabs.all_UiDemo.Scroll;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Abstract scrollable item
 */
public abstract class ScrollItem {

    abstract public ScrollItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position);

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ItemType.TYPE_PLANET, ItemType.TYPE_MAP})
    public @interface ItemType {
        int TYPE_PLANET = 10;
        int TYPE_MAP = 20;
    }

    @ItemType
    abstract public int getItemType();
}