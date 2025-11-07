package com.tad.bookshelf.domain.mapper

import com.tad.bookshelf.data.remote.*   // VolumesResponse/VolumeDto/... từ DTO của bạn
import com.tad.bookshelf.domain.model.Book
import com.tad.bookshelf.domain.model.Price

private fun https(url: String?): String? =
    url?.replaceFirst("http://", "https://")

private fun VolumeInfoDto?.isbn13(): String? =
    this?.industryIdentifiers
        ?.firstOrNull { it.type.equals("ISBN_13", ignoreCase = true) }
        ?.identifier

fun VolumeDto.toDomain(): Book {
    val vi = volumeInfo
    val si = saleInfo
    val ai = accessInfo

    val thumb = https(vi?.imageLinks?.thumbnail)
    val webReader = https(ai?.webReaderLink) ?: https(vi?.previewLink)

    val list = si?.listPrice
    val retail = si?.retailPrice

    return Book(
        id = id,
        title = vi?.title.orEmpty(),
        authors = vi?.authors ?: emptyList(),
        thumbnail = thumb,
        description = vi?.description,
        isbn13 = vi.isbn13(),
        pageCount = vi?.pageCount,
        language = vi?.language,
        previewUrl = webReader,
        infoUrl = https(vi?.infoLink),
        buyUrl = https(si?.buyLink),
        saleability = si?.saleability,
        listPrice = if (list?.amount != null && list.currencyCode != null)
            Price(list.amount, list.currencyCode) else null,
        retailPrice = if (retail?.amount != null && retail.currencyCode != null)
            Price(retail.amount, retail.currencyCode) else null,
        epubAvailable = ai?.epub?.isAvailable == true,
        pdfAvailable = ai?.pdf?.isAvailable == true
    )
}
