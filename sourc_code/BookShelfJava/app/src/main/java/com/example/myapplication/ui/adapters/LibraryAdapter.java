package com.example.myapplication.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder> {
    private final List<Book> books = new ArrayList<>();
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    private OnMoreClickListener moreClickListener;
    /** Click vào cả card để mở chi tiết sách */
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }
    /** Long click để xoá / thay đổi trạng thái... */
    public interface OnItemLongClickListener {
        void onItemLongClick(Book book);
    }
    /** Click nút 3 chấm (btn_more) trên từng item */
    public interface OnMoreClickListener {
        void onMoreClick(View anchor, Book book);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }
    public void setOnMoreClickListener(OnMoreClickListener listener) {
        this.moreClickListener = listener;
    }
    public void setBooks(List<Book> newBooks) {
        books.clear();
        if (newBooks != null) {
            books.addAll(newBooks);
        }
        notifyDataSetChanged();
    }
    public Book getBookAt(int position) {
        return books.get(position);
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_library_book, parent, false);
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Book book = books.get(position);
        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText(book.getAuthors());
        String imageUrl = book.getThumbnail();
        if(imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_cover)
                    .error(R.drawable.placeholder_cover)
                    .into(holder.imgCover);
        }else {
            holder.imgCover.setImageResource(R.drawable.placeholder_cover);
        }
        int progress = mapReadingStatusToProgress(book);
        holder.pbProgress.setProgress(progress);
        holder.tvProgress.setText(" " + progress + "%");
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(book);
            }
        });
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(book);
                return true;
            }
            return false;
        });
        holder.btnMore.setOnClickListener(v -> {
            if (moreClickListener != null) {
                moreClickListener.onMoreClick(v, book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
    private int mapReadingStatusToProgress(Book book){
        int status = book.getReadingStatus();
        switch (status) {
            case 1: // Đang đọc
                return 30;
            case 2: // Đã tải
                return 60;
            case 3: // Hoàn thành
                return 100;
            case 0:
            default:
                return 0;
        }
    }
    static class LibraryViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCover;
        ImageButton btnMore;
        TextView tvTitle;
        TextView tvAuthor;
        ProgressBar pbProgress;
        TextView tvProgress;
        LibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.img_cover);
            btnMore = itemView.findViewById(R.id.btn_more);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            pbProgress = itemView.findViewById(R.id.pb_progress);
            tvProgress = itemView.findViewById(R.id.tv_progress);
        }
    }
}
