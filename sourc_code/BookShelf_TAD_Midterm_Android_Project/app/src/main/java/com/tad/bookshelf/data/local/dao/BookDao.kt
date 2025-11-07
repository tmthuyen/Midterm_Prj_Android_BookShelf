package com.tad.bookshelf.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tad.bookshelf.data.local.entities.BookEntity

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<BookEntity>)

    @Query("SELECT * FROM books WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): BookEntity?

    // Offline search đơn giản (LIKE) — có index title
    @Query("SELECT * FROM books WHERE title LIKE '%' || :q || '%' ORDER BY title LIMIT :limit OFFSET :offset")
    suspend fun searchLocal(q: String, limit: Int, offset: Int): List<BookEntity>
}