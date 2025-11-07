package com.tad.bookshelf.ui.screen

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun PreviewScreen(url: String, onBack: () -> Unit) {
    val context = LocalContext.current
    // Cách 1: Custom Tabs (mở ngoài)
    LaunchedEffect(url) {
        CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(url))
        onBack() // quay lại ngay sau khi mở
    }
    // Cách 2: WebView nhúng (nếu muốn hiển thị trong app)
    /*
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx -> WebView(ctx).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            loadUrl(url)
        }}
    )
    */
}
