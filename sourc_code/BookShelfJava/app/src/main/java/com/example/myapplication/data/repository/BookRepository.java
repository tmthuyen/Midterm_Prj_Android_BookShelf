package com.example.myapplication.data.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.data.remote.ApiClient;
import com.example.myapplication.data.remote.ApiServer;
import com.example.myapplication.data.local.dao.BookDao;
import com.example.myapplication.data.local.BookDatabase;
import com.example.myapplication.ui.adapters.SearchFilter;
import com.example.myapplication.model.Book;
import com.example.myapplication.model.VolumeItem;
import com.example.myapplication.model.VolumeResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepository {
    private final BookDao bookDao;
    private final ApiServer apiServer;
    private final Executor executor;
    private final MutableLiveData<List<VolumeItem>>remoteSearchResults = new MutableLiveData<>();
    public BookRepository(Application application) {
        BookDatabase db = BookDatabase.getInstance(application);
        bookDao = db.bookDao();
        apiServer = ApiClient.getApiService();
        executor = Executors.newSingleThreadExecutor();
    }
    public void searchBooksOnline(String query, SearchFilter filter){
        if (query == null) {
            query = "";
        }
        String apiFilter = null;
        String download = null;

        switch (filter) {
            case PREVIEW:
                apiFilter = "partial";
                break;
            case FREE:
                apiFilter = "free-ebooks";
                break;
            case EPUB:
                download = "epub";
                break;
            case ALL:
            default:
                break;
        }
        Integer maxResults = 20;

        apiServer.searchBooks(query, apiFilter, download, maxResults).enqueue(new Callback<VolumeResponse>() {
            @Override
            public void onResponse(Call<VolumeResponse> call, Response<VolumeResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<VolumeItem> items = response.body().getItems();
                    if (items == null) {
                        items = new ArrayList<>();
                    }
                    remoteSearchResults.postValue(items);
                }else {
                    remoteSearchResults.postValue(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<VolumeResponse> call, Throwable t) {
                remoteSearchResults.postValue(new ArrayList<>());
            }
        });
    }
    public LiveData<List<VolumeItem>> getRemoteSearchResults(){
        return remoteSearchResults;
    }
    public LiveData<List<Book>> getAllBooks(){
        return bookDao.getAllBooks();
    }
    public void insertBook(Book book){
        executor.execute(() -> {
            try{
                bookDao.insert(book);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public void deleteBook(Book book){
        executor.execute(() -> bookDao.delete(book));
    }
    public LiveData<Integer> countBookById(String id){
        return bookDao.countBookById(id);
    }
    public LiveData<Book> getBookById(String id){
        return bookDao.getBookById(id);
    }
    public Book getBookByIdSync(String id){
        return bookDao.getBookByIdSync(id);
    }
    public LiveData<List<Book>> searchBooksByTitle(String keyword){
        return bookDao.searchBooksByTitle(keyword);
    }
    public LiveData<List<Book>> searchBooksWithPreview(String keyword){
        return bookDao.searchBooksWithPreview(keyword);
    }
    public LiveData<List<Book>> searchFreeBooks(){
        return bookDao.getFreeBooks();
    }
    public LiveData<List<Book>> searchEpubBooks(String keyword) {
        return bookDao.searchEpubBooks(keyword);
    }
    public LiveData<List<Book>> getBooksHavePreview() {
        return bookDao.getBooksHavePreview();
    }
    public LiveData<List<Book>> getFreeBooks() {
        return bookDao.getFreeBooks();
    }
    public LiveData<List<Book>> getEpubBooks() {
        return bookDao.getEpubBooks();
    }

    public LiveData<List<Book>> getBooksByStatus(int status){
        return bookDao.getBooksByStatus(status);
    }
    public LiveData<List<Book>> getReadingBooks() {
        return bookDao.getReadingBooks();
    }

    public LiveData<List<Book>> getDownloadedBooks() {
        return bookDao.getDownloadedBooks();
    }

    public LiveData<List<Book>> getCompletedBooks() {
        return bookDao.getCompletedBooks();
    }
    public void updateReadingStatus(String bookId, int status) {
        executor.execute(() -> bookDao.updateReadingStatus(bookId, status));
    }
}
