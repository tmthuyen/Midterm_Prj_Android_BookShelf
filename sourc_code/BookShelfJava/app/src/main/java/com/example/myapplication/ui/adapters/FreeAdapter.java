package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.VolumeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FreeAdapter extends RecyclerView.Adapter<BookViewHolder>{
    private final Context context;
    private final List<VolumeItem> books = new ArrayList<>();
    public interface OnItemClickListener {
        void onItemClick(VolumeItem item);
    }

    public interface OnMoreClickListener {
        void onMoreClick(View anchor, VolumeItem item);
    }

    private OnItemClickListener itemClickListener;
    private OnMoreClickListener moreClickListener;
    public FreeAdapter(Context context) {
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setOnMoreClickListener(OnMoreClickListener listener) {
        this.moreClickListener = listener;
    }
    public void setItems(List<VolumeItem> items) {
        books.clear();
        if (items != null) {
            books.addAll(items);
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
            holder.tvTitle.setText(item.getVolumeInfo().getTitle());

            List<String> authors = item.getVolumeInfo().getAuthors();
            holder.tvAuthor.setText(authors != null && !authors.isEmpty()
                    ? android.text.TextUtils.join(", ", authors) : "");

            String thumbnail = null;
            if (item.getVolumeInfo().getImageLinks() != null) {
                thumbnail = item.getVolumeInfo().getImageLinks().getThumbnail();
            }
            if (thumbnail != null && !thumbnail.isEmpty()) {
                Picasso.get()
                        .load(thumbnail)
                        .placeholder(R.drawable.placeholder_cover)
                        .error(R.drawable.placeholder_cover)
                        .into(holder.ivCover);
            } else {
                holder.ivCover.setImageResource(R.drawable.placeholder_cover);
            }
        }
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(item);
            }
        });

        // Click nút more giống SearchAdapter
        if (holder.btnMore != null) {
            holder.btnMore.setOnClickListener(v -> {
                if (moreClickListener != null) {
                    moreClickListener.onMoreClick(holder.btnMore, item);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
