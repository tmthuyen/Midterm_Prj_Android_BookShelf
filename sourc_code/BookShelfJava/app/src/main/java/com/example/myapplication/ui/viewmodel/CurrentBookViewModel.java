package com.example.myapplication.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.Book;

public class CurrentBookViewModel extends ViewModel {
    private MutableLiveData<Book> _currentBook = new MutableLiveData<>();

    public void setCurrentBook(Book book) {
        _currentBook.setValue(book);
    }

    public LiveData<Book> getCurrentBook() {
        return _currentBook;
    }
}
