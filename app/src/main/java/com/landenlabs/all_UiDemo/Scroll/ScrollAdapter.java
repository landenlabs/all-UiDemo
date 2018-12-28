package com.landenlabs.all_UiDemo.Scroll;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Recycler Adapter to manage ScrollItemHolder(s) and ScrollItem(s)
 */
public class ScrollAdapter extends RecyclerView.Adapter<ScrollItemHolder> {

    private final Context context;
    private final ArrayList<ScrollItem> items;

    // Collection of view holders by type.
    private SparseArray<ScrollItem> holderMakersByType = new SparseArray();

    // False to make new holder per position
    // True reuse holders by item type.
    static final boolean holdersByType = true;

    public ScrollAdapter(Context context, ArrayList<ScrollItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (holdersByType) {
            ScrollItem item = items.get(position);
            int viewType = item.getItemType();
            holderMakersByType.append(viewType, item);
            return viewType;
        } else {
            return position;
        }
    }

    @NonNull
    @Override
    public ScrollItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (holdersByType) {
            return holderMakersByType.get(viewType).onCreateViewHolder(parent, viewType);
        }  else {
            int position = viewType;
            return items.get(position).onCreateViewHolder(parent, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ScrollItemHolder scrollItemHolder, int position) {
        ScrollItem item = items.get(position);
        scrollItemHolder.onBindViewHolder(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ScrollItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ScrollItemHolder holder) {
        holder.onViewDetachedFromWindow();
        super.onViewDetachedFromWindow(holder);
    }
}
