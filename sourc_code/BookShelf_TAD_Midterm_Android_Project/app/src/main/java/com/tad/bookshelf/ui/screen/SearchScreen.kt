package com.tad.bookshelf.ui.screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.tad.bookshelf.domain.model.Book
import com.tad.bookshelf.ui.components.BookRow
import com.tad.bookshelf.ui.viewmodel.SearchBookViewModel
import com.tad.bookshelf.utils.UrlUtil
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    nav: NavHostController,
    searchVM: SearchBookViewModel,
    onOpenDetail: (String) -> Unit
)
{
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
//        topBar = { TopAppBar(title = { Text("BookShelf (Local)") }) }
        modifier = Modifier.fillMaxSize(),                 // ✅
        topBar = { TopAppBar(title = { Text("BookShelf (Local)") }) },
        contentWindowInsets = WindowInsets(0)
    ) { p ->
        Column(Modifier.padding(p).padding(12.dp)) {
            OutlinedTextField(
                value = searchVM.q,
                onValueChange = { searchVM.q = it },
                label = { Text("Tìm sách (tiêu đề/tác giả/ISBN)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardActions = KeyboardActions(
                    onSearch = { searchVM.search() } // search() không suspend
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = searchVM::search, enabled = !searchVM.isLoading) {
                Text("Tìm")
            }
            if (searchVM.isLoading)
                LinearProgressIndicator(Modifier.fillMaxWidth().padding(top = 8.dp))
            searchVM.error?.let {
                Text("Lỗi: $it", color = MaterialTheme.colorScheme.error)
                Log.e("SearchScreen", "Error: $it")
            }
            LazyColumn(Modifier.fillMaxSize()) {
                items(searchVM.books) { b ->
                    BookRow(
                        b,
                        onPreview = { b.previewUrl?.let { UrlUtil.openUrl(context, it) } },
                        onBuy = { b.buyUrl?.let { UrlUtil.openUrl(context, it) } },
                        onSave = { scope.launch { searchVM.save(b) } },
                        onOpen = { onOpenDetail(b.id) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}


