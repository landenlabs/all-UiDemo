package com.landenlabs.all_UiDemo.Scroll;

/*
 * Copyright (c) 2019 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (3/21/2015)
 *  @see http://landenlabs.com
 *
 */

import android.content.Context;
import android.util.SparseArray;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Recycler Adapter to manage ScrollItemHolder(s) and ScrollItem(s)
 */
@SuppressWarnings("UnnecessaryLocalVariable")
public class ScrollAdapter extends RecyclerView.Adapter<ScrollItemHolder> {

    private final ArrayList<ScrollItem> items;

    // Collection of view holders by type.
    private final SparseArray<ScrollItem> holderMakersByType = new SparseArray<>();

    // False to make new holder per position
    // True reuse holders by item type.
    static final boolean holdersByType = true;

    public ScrollAdapter(Context context, ArrayList<ScrollItem> items) {
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
