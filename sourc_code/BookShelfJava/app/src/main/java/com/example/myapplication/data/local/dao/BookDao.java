package com.example.myapplication.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.myapplication.model.Book;

import java.util.List;
@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);
    @Delete
    void delete(Book book);
    @Query("SELECT * FROM books ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT COUNT(*) FROM books WHERE id = :id")
    LiveData<Integer> countBookById(String id);

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    LiveData<Book> getBookById(String id);
    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    Book getBookByIdSync(String id);
    @Query("SELECT * FROM books " +
            "WHERE title LIKE '%' || :keyword || '%' " +
            "ORDER BY title ASC")
    LiveData<List<Book>> searchBooksByTitle(String keyword);
    @Query("SELECT * FROM books " +
            "WHERE readingStatus = :status " +
            "ORDER BY title ASC")
    LiveData<List<Book>> getBooksByStatus(int status);
    @Query("SELECT * FROM books " +
            "WHERE readingStatus = 1 " +
            "ORDER BY title ASC")
    LiveData<List<Book>> getReadingBooks();
    @Query("SELECT * FROM books " +
            "WHERE readingStatus = 2 " +
            "ORDER BY title ASC")
    LiveData<List<Book>> getDownloadedBooks();
    @Query("SELECT * FROM books " +
            "WHERE readingStatus = 3 " +
            "ORDER BY title ASC")
    LiveData<List<Book>> getCompletedBooks();
    @Query("UPDATE books SET readingStatus = :status WHERE id = :id")
    void updateReadingStatus(String id, int status);
    @Query("SELECT * FROM books " +
            "WHERE previewLink IS NOT NULL AND previewLink != '' " +
            "ORDER BY title ASC")
    LiveData<List<Book>> getBooksHavePreview();
    @Query("SELECT * FROM books " +
            "WHERE isFree = 1 " +
            "ORDER BY title ASC")
    LiveData<List<Book>> getFreeBooks();
    @Query("SELECT * FROM books " +
            "WHERE hasEpub = 1 " +
            "ORDER BY title ASC")
    LiveData<List<Book>> getEpubBooks();
    @Query("SELECT * FROM books " +
            "WHERE title LIKE '%' || :keyword || '%' " +
            "AND previewLink IS NOT NULL AND previewLink != '' " +
            "ORDER BY title ASC")
    LiveData<List<Book>> searchBooksWithPreview(String keyword);
    @Query("SELECT * FROM books " +
            "WHERE title LIKE '%' || :keyword || '%' " +
            "AND isFree = 1 " +
            "ORDER BY title ASC")
    LiveData<List<Book>> searchFreeBooks(String keyword);
    @Query("SELECT * FROM books " +
            "WHERE title LIKE '%' || :keyword || '%' " +
            "AND hasEpub = 1 " +
            "ORDER BY title ASC")
    LiveData<List<Book>> searchEpubBooks(String keyword);
}
