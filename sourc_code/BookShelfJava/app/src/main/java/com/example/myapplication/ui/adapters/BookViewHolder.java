package com.example.myapplication.ui.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class BookViewHolder extends RecyclerView.ViewHolder{
    public ImageView ivCover;
    public TextView tvTitle;
    public TextView tvAuthor;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        ivCover  = itemView.findViewById(R.id.img_cover);
        tvTitle  = itemView.findViewById(R.id.tv_title);
        tvAuthor = itemView.findViewById(R.id.tv_author);
    }
}
