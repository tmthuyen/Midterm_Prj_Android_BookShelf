package com.tad.bookshelf.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class SimpleFactory<T : ViewModel>(private val create: () -> T) : ViewModelProvider.Factory {

    // API mới (Lifecycle 2.5+). Compose/Lifecycle 2.8.x sẽ gọi hàm này.
    override fun <VM : ViewModel> create(modelClass: Class<VM>, extras: CreationExtras): VM {
        @Suppress("UNCHECKED_CAST")
        return create() as VM
    }

    // Giữ cho tương thích ngược (có thể bị @Deprecated)
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
        return create() as VM
    }
}
