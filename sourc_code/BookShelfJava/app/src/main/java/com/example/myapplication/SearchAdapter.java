package com.example.myapplication;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.model.VolumeInfo;
import com.example.myapplication.model.VolumeItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final List<VolumeItem> items = new ArrayList<>();
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(VolumeItem item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setItems(List<VolumeItem> newItems) {
        items.clear();
        if(newItems != null){
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_library_book, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        VolumeItem item = items.get(position);
        VolumeInfo info = item.getVolumeInfo();
        if(info != null){
            holder.titleText.setText(info.getTitle() != null ? info.getTitle() : "");
            if(info.getAuthors() != null && !info.getAuthors().isEmpty()){
                holder.authorText.setText(TextUtils.join(", ", info.getAuthors()));
            }else {
                holder.authorText.setText(R.string.unknown_author);
            }
            ImageLinks links = info.getImageLinks();
            String imageUrl = null;
            if (links != null) {
                imageUrl = links.getThumbnail();
                if (imageUrl == null) {
                    imageUrl = links.getSmallThumbnail();
                }
            }
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.placeholder_cover)
                        .into(holder.coverImageView);
            } else {
                holder.coverImageView.setImageResource(R.drawable.placeholder_cover);
            }
        }else {
            holder.titleText.setText("");
            holder.authorText.setText(R.string.unknown_author);
            holder.coverImageView.setImageResource(R.drawable.placeholder_cover);
        }
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder{
        ImageView coverImageView;
        TextView titleText;
        TextView authorText;
        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImageView = itemView.findViewById(R.id.img_cover);
            titleText = itemView.findViewById(R.id.tv_title);
            authorText = itemView.findViewById(R.id.tv_author);
        }

    }
}
