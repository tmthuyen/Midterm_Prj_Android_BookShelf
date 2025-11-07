package com.tad.bookshelf.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tad.bookshelf.core.SimpleFactory
import com.tad.bookshelf.data.local.AppDatabase
import com.tad.bookshelf.data.remote.provideApi
import com.tad.bookshelf.data.repository.BookRepository
import com.tad.bookshelf.ui.nav.AppNav
import com.tad.bookshelf.ui.viewmodel.DetailViewModel
import com.tad.bookshelf.ui.viewmodel.LibraryViewModel
import com.tad.bookshelf.ui.viewmodel.SearchBookViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val api = provideApi()
        val db = AppDatabase.getInstance(this)
        val repo = BookRepository(api, db)

        setContent {
            val searchVM: SearchBookViewModel = viewModel(factory = SimpleFactory { SearchBookViewModel(repo) })
            val libraryVM: LibraryViewModel = viewModel(factory = SimpleFactory { LibraryViewModel(repo) })
            val detailViewModel: DetailViewModel = viewModel(factory = SimpleFactory { DetailViewModel(repo) })

            AppNav(searchVM, libraryVM, detailViewModel)
        }
    }
}