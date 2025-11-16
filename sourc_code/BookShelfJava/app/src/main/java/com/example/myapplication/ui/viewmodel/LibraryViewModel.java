package com.example.myapplication.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.data.repository.BookRepository;
import com.example.myapplication.model.Book;

import java.util.List;

public class LibraryViewModel extends AndroidViewModel {
    private final BookRepository repository;
    public LibraryViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
    }
    public LiveData<List<Book>> getAllBooks() {
        return repository.getAllBooks();
    }

    public LiveData<List<Book>> getReadingBooks() {
        return repository.getReadingBooks();
    }
    public LiveData<List<Book>> getDownloadedBooks() {
        return repository.getDownloadedBooks();
    }
    public LiveData<List<Book>> getCompletedBooks() {
        return repository.getCompletedBooks();
    }
    public void setReadingStatus(String bookId, int status) {
        repository.updateReadingStatus(bookId, status);
    }
    public void addBook(Book book) {
        repository.insertBook(book);
    }
    public void removeBook(Book book) {
        repository.deleteBook(book);
    }
    public LiveData<Integer> countBookById(String id) {
        return repository.countBookById(id);
    }
    public LiveData<Book> getBookById(String id) {
        return repository.getBookById(id);
    }
    public LiveData<List<Book>> getBooksHavePreview() {
        return repository.getBooksHavePreview();
    }
    public LiveData<List<Book>> getFreeBooks() {
        return repository.getFreeBooks();
    }
    public LiveData<List<Book>> getEpubBooks() {
        return repository.getEpubBooks();
    }
    public LiveData<List<Book>> getBooksByStatus(int status){
        return repository.getBooksByStatus(status);
    }
}
