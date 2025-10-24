package com.tad.bookshelf.data.model

class Book {

}

data class BookResponse(
    val items: List<BookItem>?
)

data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo?,
    val accessInfo: AccessInfo?,
    val searchInfo: SearchInfo?
)

data class VolumeInfo(
    val title: String?,
    val subtitle: String?,
    val authors: List<String>?,
    val publishedDate: String?,
    val description: String?,
    val imageLinks: ImageLinks?,
    val previewLink: String?
)

data class ImageLinks(
    val smallThumbnail: String?,
    val thumbnail: String?
)

data class SaleInfo(
    val buyLink: String?
)

data class AccessInfo(
    val webReaderLink: String?,
    val pdf: Pdf?,
    val epub: Epub?
)

data class Pdf(
    val isAvailable: Boolean?,
    val downloadLink: String?
)

data class Epub(
    val isAvailable: Boolean?,
    val downloadLink: String?
)

data class SearchInfo(
    val textSnippet: String?
)
