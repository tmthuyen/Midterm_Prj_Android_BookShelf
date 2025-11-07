package com.tad.bookshelf.ui.nav

// ui/nav/Routes.kt
object Routes {
    const val SEARCH = "search"
    const val LIBRARY = "library"
    const val DETAIL = "detail/{id}"
    fun detail(id: String) = "detail/$id"
    const val PREVIEW = "preview?url={url}"
    fun preview(url: String) = "${java.net.URLEncoder.encode(url, "UTF-8")}"
}
