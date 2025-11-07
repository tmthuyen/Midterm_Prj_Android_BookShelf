package com.tad.bookshelf.utils

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri

object UrlUtil {
    @JvmStatic
    fun openUrl(context: Context, url: String) {
        CustomTabsIntent.Builder().build()
            .launchUrl(context, url.toUri())
    }
}