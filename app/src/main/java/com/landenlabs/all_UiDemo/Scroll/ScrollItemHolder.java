package com.landenlabs.all_UiDemo.Scroll;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *  Abstract Recycler View Holder for items
 */
public abstract class ScrollItemHolder extends RecyclerView.ViewHolder {

    public ScrollItemHolder(@NonNull View itemView) {
        super(itemView);
    }

    abstract public void onBindViewHolder(ScrollItem item);

    public void onViewAttachedToWindow() {
    }
    public void onViewDetachedFromWindow() {
    }
}
