package com.tad.bookshelf.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tad.bookshelf.data.repository.BookRepository
import com.tad.bookshelf.domain.model.Book
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repo: BookRepository
) : ViewModel() {
    var book by mutableStateOf<Book?>(null)
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun loadDetail(id: String) = viewModelScope.launch {
        isLoading = true
        error = null
        try {
            book = repo.getDetail(id)
        }
        catch (t: Throwable) {
            error = t.message
        }
        finally {
            isLoading = false
        }
    }
}

