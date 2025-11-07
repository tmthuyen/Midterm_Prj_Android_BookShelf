package com.tad.bookshelf.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.tad.bookshelf.ui.components.BookRow
import com.tad.bookshelf.ui.viewmodel.LibraryViewModel
import com.tad.bookshelf.utils.UrlUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    nav: NavHostController,
    vm: LibraryViewModel,
    onOpenDetail: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) { vm.load(0, 20) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),                 // ✅
        topBar = { TopAppBar(title = { Text("Danh sách yêu thích") }) },
        contentWindowInsets = WindowInsets(0)
    ) { p ->
        Box(Modifier.padding(p)) {
            when {
                vm.isLoading -> LinearProgressIndicator(Modifier.fillMaxWidth().padding(12.dp))
                vm.error != null -> Text("Lỗi: ${vm.error}", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(12.dp))
                vm.books.isEmpty() -> Text("Chưa có sách nào. Hãy lưu từ kết quả tìm kiếm.", modifier = Modifier.padding(12.dp))
                else -> LazyColumn {
                    items(vm.books) { b ->
                        BookRow(
                            b = b,
                            onPreview = { b.previewUrl?.let { UrlUtil.openUrl(context, it) } },
                            onOpen = { onOpenDetail(b.id) },
                            onRemove = { scope.launch { vm.remove(b.id); vm.load(0, 20) } },
                            isSaved = true
                        )
                        HorizontalDivider(  )
                    }
                }
            }
        }
    }
}
