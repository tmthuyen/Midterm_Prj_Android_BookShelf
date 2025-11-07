package com.tad.bookshelf.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tad.bookshelf.data.local.AppDatabase
import com.tad.bookshelf.data.remote.GoogleBooksApi
import com.tad.bookshelf.data.repository.BookRepository
import com.tad.bookshelf.domain.model.Book
import kotlinx.coroutines.launch

class LibraryViewModel(private val repo: BookRepository) : ViewModel() {
    var books by mutableStateOf<List<Book>>(emptyList())
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun load(page: Int = 0, pageSize: Int = 20) = viewModelScope.launch {
        isLoading = true; error = null
        try { books = repo.getLibrary(page, pageSize) } catch (t: Throwable) { error = t.message }
        finally { isLoading = false }
    }
    suspend fun remove(id: String) = repo.removeFavorite(id)
}
