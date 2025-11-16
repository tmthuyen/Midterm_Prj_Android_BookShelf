package com.example.myapplication.model.mappers;

import android.util.Log;

import com.example.myapplication.model.AccessInfo;
import com.example.myapplication.model.Book;
import com.example.myapplication.model.SaleInfo;
import com.example.myapplication.model.VolumeInfo;
import com.example.myapplication.model.VolumeItem;

import retrofit2.http.Tag;

public class BookMapper {

    public static Book fromVolumeItem(VolumeItem item) {
        if (item == null || item.getVolumeInfo() == null) return null;

        VolumeInfo info = item.getVolumeInfo();
        SaleInfo sale = item.getSaleInfo();
        AccessInfo access = item.getAccessInfo();

        // Authors: List<String> -> "A, B, C"
        String authorsStr = null;
        if (info.getAuthors() != null && !info.getAuthors().isEmpty()) {
            authorsStr = android.text.TextUtils.join(", ", info.getAuthors());
        }

        // Categories: List<String> -> "X, Y, Z"
        String categoriesStr = null;
        if (info.getCategories() != null && !info.getCategories().isEmpty()) {
            categoriesStr = android.text.TextUtils.join(", ", info.getCategories());
        }

        // Thumbnail
        String thumbnail = null;
        if (info.getImageLinks() != null) {
            thumbnail = info.getImageLinks().getThumbnail();
            if (thumbnail == null) {
                thumbnail = info.getImageLinks().getSmallThumbnail();
            }
        }

        // isFree: dựa vào saleability = FREE (có thể tinh chỉnh thêm nếu muốn)
        boolean isFree = false;
        if (sale != null && sale.getSaleability() != null) {
            isFree = "FREE".equalsIgnoreCase(sale.getSaleability());
        }

        // hasEpub: dựa vào accessInfo.epub.isAvailable
        boolean hasEpub = access != null
                && access.getEpub() != null
                && access.getEpub().getIsAvailable();

        Book book = new Book();
        book.setId(item.getId());
        book.setTitle(info.getTitle());
        book.setAuthors(authorsStr);
        book.setDescription(info.getDescription());
        book.setThumbnail(thumbnail);
        book.setPreviewLink(info.getPreviewLink());
        book.setAverageRating(info.getAverageRating() != null ? info.getAverageRating() : 0.0);
        book.setRatingsCount(info.getRatingsCount() != null ? info.getRatingsCount() : 0);
        book.setPageCount(info.getPageCount() != null ? info.getPageCount() : 0);
        book.setPublishedDate(info.getPublishedDate());
        book.setLanguage(info.getLanguage());
        book.setPublisher(info.getPublisher());
        book.setCategories(categoriesStr);
        book.setFormat(info.getPrintType());      // "BOOK", "MAGAZINE"...
        book.setFree(isFree);
        book.setHasEpub(hasEpub);
        book.setReadingStatus(Book.STATUS_DEFAULT);
        Log.d("Mapper", "fromVolumeItem: " + book.getAverageRating());
        return book;
    }
}
