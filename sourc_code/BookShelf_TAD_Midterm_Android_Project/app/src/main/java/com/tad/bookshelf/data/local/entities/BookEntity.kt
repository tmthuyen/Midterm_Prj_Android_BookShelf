package com.tad.bookshelf.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    indices = [
        Index(value = ["title"]),           // tìm nhanh theo tiêu đề
        Index(value = ["isbn13"]),          // tra ISBN nhanh
    ]
)
data class BookEntity(
    @PrimaryKey val id: String,
    val title: String,
    /** authors join bằng "|" để lưu 1 cột */
    val authors: String,
    val thumbnail: String?,
    val description: String?,

    // Thông tin thêm
    val isbn13: String? = null,
    val pageCount: Int? = null,
    val language: String? = null,

    // Links
    val previewUrl: String? = null,
    val infoUrl: String? = null,
    val buyUrl: String? = null,
    val saleability: String? = null,

    // Giá (flatten)
    val listAmount: Double? = null,
    val listCurrency: String? = null,
    val retailAmount: Double? = null,
    val retailCurrency: String? = null,

    // Khả dụng định dạng
    val epubAvailable: Boolean = false,
    val pdfAvailable: Boolean = false,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)