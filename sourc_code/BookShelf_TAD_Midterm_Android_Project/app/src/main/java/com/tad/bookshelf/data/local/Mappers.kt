package com.tad.bookshelf.data.local


import com.tad.bookshelf.data.local.entities.BookEntity
import com.tad.bookshelf.domain.model.Book
import com.tad.bookshelf.domain.model.Price

fun BookEntity.toDomain(): Book =
    Book(
        id = id,
        title = title,
        authors = authors.split("|").filter { it.isNotBlank() },
        thumbnail = thumbnail,
        description = description,
        isbn13 = isbn13,
        pageCount = pageCount,
        language = language,
        previewUrl = previewUrl,
        infoUrl = infoUrl,
        buyUrl = buyUrl,
        saleability = saleability,
        listPrice = if (listAmount != null && listCurrency != null)
            Price(listAmount, listCurrency) else null,
        retailPrice = if (retailAmount != null && retailCurrency != null)
            Price(retailAmount, retailCurrency) else null,
        epubAvailable = epubAvailable,
        pdfAvailable = pdfAvailable
    )

fun Book.toEntity(): BookEntity =
    BookEntity(
        id = id,
        title = title,
        authors = authors.joinToString("|"),
        thumbnail = thumbnail,
        description = description,
        isbn13 = isbn13,
        pageCount = pageCount,
        language = language,
        previewUrl = previewUrl,
        infoUrl = infoUrl,
        buyUrl = buyUrl,
        saleability = saleability,
        listAmount = listPrice?.amount,
        listCurrency = listPrice?.currencyCode,
        retailAmount = retailPrice?.amount,
        retailCurrency = retailPrice?.currencyCode,
        epubAvailable = epubAvailable,
        pdfAvailable = pdfAvailable
    )
