package com.tad.bookshelf.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tad.bookshelf.domain.model.Book

@Composable
fun BookRow(
    b: Book,
    onPreview: () -> Unit = {},
    onBuy: () -> Unit = {},
    onSave: () -> Unit = {},
    onOpen: () -> Unit = {},
    onRemove: () -> Unit = {},
    isSaved: Boolean = false,
) {
    Row(
        Modifier.fillMaxWidth()
            .clickable { onOpen() }
            .padding(12.dp)
    ) {
        AsyncImage(model = b.thumbnail, contentDescription = null, modifier = Modifier.size(64.dp))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(b.title, style = MaterialTheme.typography.titleMedium, maxLines = 2)
            if (b.authors.isNotEmpty())
                Text(b.authors.joinToString(), style = MaterialTheme.typography.bodySmall)
            Row {
                b.retailPrice?.let { p ->
                    Text("${p.amount.toInt()} ${p.currencyCode}", style = MaterialTheme.typography.labelMedium)
                } ?: if (b.saleability == "FREE") {
                    Text("FREE", style = MaterialTheme.typography.labelMedium)
                } else {

                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (b.previewUrl != null) TextButton(onClick = onPreview) { Text("Preview") }
                if (b.buyUrl != null) TextButton(onClick = onBuy) { Text("Mua") }
                if (isSaved)
                    TextButton(onClick = onRemove) { Text("Xóa") }
                else
                    TextButton(onClick = onSave) { Text("Thêm yêu thích") }
            }
        }
    }
}