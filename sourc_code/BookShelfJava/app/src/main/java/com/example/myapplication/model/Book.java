package com.example.myapplication.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
@Entity(tableName = "books")
public class Book implements Serializable {
    public static final int STATUS_DEFAULT = 0;
    public static final int STATUS_READING = 1;
    public static final int STATUS_DOWNLOADED = 2;
    public static final int STATUS_COMPLETED = 3;
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String authors;
    private String description;
    private String thumbnail;
    private String previewLink;
    private Double averageRating;
    private int ratingsCount;
    private int pageCount;
    private String publishedDate;
    private String language;
    private String publisher;
    private String categories;
    private String format;
    private boolean isFree;
    private boolean hasEpub;

    /**
     * Trạng thái đọc:
     * 0 = chưa xác định / mặc định
     * 1 = Đang đọc
     * 2 = Đã tải
     * 3 = Hoàn thành
     */
    private int readingStatus;

    public Book(){}

    public Book(@NonNull String id, String title, String authors, String description,
                String thumbnail, String previewLink,Double averageRating,
                int ratingsCount,
                int pageCount,
                String publishedDate,
                String language,
                String publisher,
                String categories,
                String format,
                int readingStatus,
                boolean isFree,
                boolean hasEpub){
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.description = description;
        this.thumbnail = thumbnail;
        this.previewLink = previewLink;
        this.averageRating = averageRating;
        this.ratingsCount = ratingsCount;
        this.pageCount = pageCount;
        this.publishedDate = publishedDate;
        this.language = language;
        this.publisher = publisher;
        this.categories = categories;
        this.format = format;
        this.readingStatus = readingStatus;
        this.isFree = isFree;
        this.hasEpub = hasEpub;
    }
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(Integer ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(int readingStatus) {
        this.readingStatus = readingStatus;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public boolean isHasEpub() {
        return hasEpub;
    }

    public void setHasEpub(boolean hasEpub) {
        this.hasEpub = hasEpub;
    }
}
