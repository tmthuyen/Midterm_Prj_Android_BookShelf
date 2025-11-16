package com.example.myapplication.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.repository.BookRepository;
import com.example.myapplication.model.Book;
import com.example.myapplication.ui.adapters.SearchFilter;
import com.example.myapplication.model.VolumeItem;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private final BookRepository repository;
    private final MutableLiveData<SearchFilter> currentFilter =
            new MutableLiveData<>(SearchFilter.ALL);
    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
    }
    public LiveData<List<VolumeItem>> getSearchResults() {
        return repository.getRemoteSearchResults();
    }
    public LiveData<SearchFilter> getCurrentFilter() {
        return currentFilter;
    }
    public void search(String query) {
        SearchFilter filter = currentFilter.getValue();
        if (filter == null) filter = SearchFilter.ALL;
        repository.searchBooksOnline(query, filter);
    }
    public void setFilter(SearchFilter filter, String currentQuery) {
        currentFilter.setValue(filter);
        if (currentQuery != null && !currentQuery.trim().isEmpty()) {
            repository.searchBooksOnline(currentQuery, filter);
        }
    }
    public void saveTolibrary(Book book){
        repository.insertBook(book);
    }
    public void deleteFromLibrary(Book book){
        repository.deleteBook(book);
    }
    public void updateReadingStatus(String bookId, int status) {
        repository.updateReadingStatus(bookId, status); // hàm này mình thấy có trong repo của bạn
    }

}
