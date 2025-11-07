package com.tad.bookshelf.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tad.bookshelf.ui.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: String,
    onBack: () -> Unit,
    onPreview: (String) -> Unit,
    vm: DetailViewModel
) {
    vm.loadDetail(id)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
                TopAppBar(
                    title = { Text(vm.book?.title ?: "Chi tiết sách") },
                    navigationIcon = { IconButton (onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )},
        contentWindowInsets = WindowInsets(0)
    ) { p ->
        if (vm.book == null) {
            Box(Modifier.padding(p).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column (Modifier.padding(p).verticalScroll(rememberScrollState()).padding(16.dp)) {
                AsyncImage(model = vm.book?.thumbnail, contentDescription = null, modifier = Modifier.height(180.dp).fillMaxWidth())
                Spacer(Modifier.height(12.dp))
                vm.book?.title?.let { Text(it, style = MaterialTheme.typography.titleLarge) }
                if (vm.book?.authors?.isNotEmpty() == true)
                    vm.book?.authors?.let { it?.joinToString()?.let { text -> Text(text, style = MaterialTheme.typography.bodyMedium) } }
                Spacer(Modifier.height(8.dp))
                Text(vm.book?.description ?: "Không có mô tả.", style = MaterialTheme.typography.bodyMedium)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    vm.book?.previewUrl?.let { url -> Button (onClick = { onPreview(url) }) { Text("Preview") } }
                }
            }
        }
    }
}

