package com.tad.bookshelf.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tad.bookshelf.data.repository.BookRepository
import com.tad.bookshelf.domain.model.Book
import kotlinx.coroutines.launch

class SearchBookViewModel(
    private val repo: BookRepository
) : ViewModel() {
    var q by mutableStateOf("")
//    private val _q = mutableStateOf("")
//    var q: String
//        get() = _q.value
//        set(value) { _q.value = value }
    var isLoading by mutableStateOf(false)
    var books by mutableStateOf<List<Book>>(emptyList())
    var error by mutableStateOf<String?>(null)
    var offlineFlag by mutableStateOf(false)


    fun search() = viewModelScope.launch {
        isLoading = true
        error = null
        val res = runCatching {
            repo.search2(q)
        }
        res.onSuccess {
            books = it.items
            offlineFlag = it.fromOffline
        }.onFailure {
            error = it.message
        }
        isLoading = false
    }


    suspend fun save(book: Book) = repo.saveFavorite(book)
}