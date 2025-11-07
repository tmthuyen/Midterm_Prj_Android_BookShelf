package com.tad.bookshelf.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val thumbnail: String?,
    val description: String?,
    val isbn13: String?,
    val pageCount: Int?,
    val language: String?,
    // Preview & mua
    val previewUrl: String?,     // ưu tiên webReaderLink nếu có
    val infoUrl: String?,
    val buyUrl: String?,
    // Tình trạng & giá
    val saleability: String?,    // FOR_SALE / NOT_FOR_SALE ...
    val listPrice: Price?,
    val retailPrice: Price?,
    // Khả dụng định dạng
    val epubAvailable: Boolean,
    val pdfAvailable: Boolean
)

data class Price(val amount: Double, val currencyCode: String)
