package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.VolumeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<BookViewHolder> {
    private final Context context;
    private final List<VolumeItem> books = new ArrayList<>();
    public TrendingAdapter(Context context) {
        this.context = context;
    }
    public void setItems(List<VolumeItem> newItems) {
        books.clear();
        if (newItems != null) {
            books.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_library_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        VolumeItem item = books.get(position);
        if (item != null && item.getVolumeInfo() != null) {
            String title = item.getVolumeInfo().getTitle();
            holder.tvTitle.setText(title != null ? title : "");

            List<String> authors = item.getVolumeInfo().getAuthors();
            if (authors != null && !authors.isEmpty()) {
                holder.tvAuthor.setText(android.text.TextUtils.join(", ", authors));
            } else {
                holder.tvAuthor.setText("");
            }

            String thumbUrl = null;
            if (item.getVolumeInfo().getImageLinks() != null) {
                thumbUrl = item.getVolumeInfo().getImageLinks().getThumbnail();
            }
            if (thumbUrl != null && !thumbUrl.isEmpty()) {
                Picasso.get()
                        .load(thumbUrl)
                        .placeholder(R.drawable.placeholder_cover)
                        .error(R.drawable.placeholder_cover)
                        .into(holder.ivCover);
            } else {
                holder.ivCover.setImageResource(R.drawable.placeholder_cover);
            }
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
