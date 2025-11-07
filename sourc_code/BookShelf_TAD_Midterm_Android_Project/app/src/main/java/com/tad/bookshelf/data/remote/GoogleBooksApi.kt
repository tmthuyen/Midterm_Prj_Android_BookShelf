package com.tad.bookshelf.data.remote

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksApi {
    @GET("volumes")
    suspend fun search(
        @Query("q") q: String,
        @Query("startIndex") start: Int = 0,
        @Query("maxResults") max: Int = 20
    ): VolumesResponse
}

fun provideApi(): GoogleBooksApi {
    val BASE_URL = "https://www.googleapis.com/books/v1/"
    val log = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val client = OkHttpClient.Builder().addInterceptor(log).build()
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
        .create(GoogleBooksApi::class.java)
}


@JsonClass(generateAdapter = true)
data class VolumesResponse(
    val kind: String? = null,
    val totalItems: Int? = null,
    val items: List<VolumeDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class VolumeDto(
    val kind: String? = null,
    val id: String,
    val volumeInfo: VolumeInfoDto? = null,
    val saleInfo: SaleInfoDto? = null,
    val accessInfo: AccessInfoDto? = null
)

@JsonClass(generateAdapter = true)
data class VolumeInfoDto(
    val title: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val imageLinks: ImageLinksDto? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val language: String? = null,
    val industryIdentifiers: List<IdentifierDto>? = null
)

@JsonClass(generateAdapter = true)
data class IdentifierDto(
    val type: String? = null,      // "ISBN_13", "ISBN_10", ...
    val identifier: String? = null
)

@JsonClass(generateAdapter = true)
data class ImageLinksDto(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)

@JsonClass(generateAdapter = true)
data class SaleInfoDto(
    val saleability: String? = null,   // "FOR_SALE", "NOT_FOR_SALE", "FREE"...
    val country: String? = null,
    val isEbook: Boolean? = null,
    val buyLink: String? = null,
    val listPrice: PriceDto? = null,
    val retailPrice: PriceDto? = null
)

@JsonClass(generateAdapter = true)
data class PriceDto(
    val amount: Double? = null,
    val currencyCode: String? = null
)

@JsonClass(generateAdapter = true)
data class AccessInfoDto(
    val country: String? = null,
    val viewability: String? = null,       // "PARTIAL", "ALL_PAGES", ...
    val embeddable: Boolean? = null,
    val publicDomain: Boolean? = null,
    val epub: FormatDto? = null,           // thêm
    val pdf: FormatDto? = null,
    val webReaderLink: String? = null,
    val accessViewStatus: String? = null   // để nullable
)

@JsonClass(generateAdapter = true)
data class FormatDto(
    val isAvailable: Boolean? = null,
    val acsTokenLink: String? = null       // có ở pdf; epub thường không có
)
