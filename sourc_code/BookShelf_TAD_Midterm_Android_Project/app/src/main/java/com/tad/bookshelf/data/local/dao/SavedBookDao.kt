package com.tad.bookshelf.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tad.bookshelf.data.local.entities.BookEntity
import com.tad.bookshelf.data.local.entities.SavedBookEntity

@Dao
interface SavedBookDao{
    // save favorite book
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(savedBook: SavedBookEntity)

    // delete favorite book
    @Query("DELETE FROM saved_books WHERE bookId = :bookId")
    suspend fun delete(bookId: String): Void

    // delete favorite book
    @Query("SELECT EXISTS(SELECT * FROM saved_books WHERE bookId = :bookId)")
    suspend fun isSaved(bookId: String): Boolean

    // Thư viện: JOIN để lấy BookEntity + order theo thời điểm lưu
    @Query("""
    SELECT b.* FROM books b
    INNER JOIN saved_books s ON s.bookId = b.id
    WHERE b.title LIKE '%' || :key || '%' 
    ORDER BY s.createdAt DESC
    LIMIT :limit OFFSET :offset
  """)
    suspend fun getLibrary(key: String = "", limit: Int = 20, offset: Int = 0): List<BookEntity>


}