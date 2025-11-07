package com.tad.bookshelf.data.repository

import androidx.room.withTransaction
import com.tad.bookshelf.data.local.AppDatabase
import com.tad.bookshelf.data.local.entities.SavedBookEntity
import com.tad.bookshelf.data.local.toDomain
import com.tad.bookshelf.data.local.toEntity
import com.tad.bookshelf.data.remote.GoogleBooksApi
import com.tad.bookshelf.domain.mapper.toDomain
import com.tad.bookshelf.domain.model.Book

class BookRepository(
    private val api: GoogleBooksApi,
    private val db: AppDatabase
) {
    private val bookDao = db.bookDao()
    private val savedDao = db.savedDao()

    // Từ web → map → upsert vào books
    suspend fun search(q: String, pageSize: Int = 30, page: Int = 0): List<Book> {
        val key = q.trim()
        return try {
            // 1) Online
            val remote = api.search(key.ifBlank { "" })
                .items.orEmpty()
                .mapNotNull { it.toDomain() }

            // 2) Upsert vào DB như “cache chung”
            db.withTransaction {
                bookDao.upsertAll(remote.map { it.toEntity() })
            }
            remote
        } catch (e: Exception) {
            // 3) Offline fallback
            bookDao.searchLocal(key, limit = pageSize, offset = page * pageSize)
                .map { it.toDomain() }
        }
    }

    suspend fun search2(q: String): SearchResult = try {
        val remote = api.search(q.ifBlank { "" })
                        .items
                        .orEmpty()
                        .mapNotNull { it.toDomain() }
        db.withTransaction {
            bookDao.upsertAll(remote.map {
                it.toEntity()
            })
        }
        SearchResult(remote, fromOffline = false)
    } catch (_: Exception) {
        val local = bookDao.searchLocal(q, 50, 0).map { it.toDomain() }
        SearchResult(local, fromOffline = true)
    }


    // Đánh dấu yêu thích
    suspend fun saveFavorite(book: Book) {
        db.withTransaction {
            bookDao.upsertAll(listOf(book.toEntity()))   // đảm bảo đã có trong books
            savedDao.save(SavedBookEntity(bookId = book.id))
        }
    }

    suspend fun removeFavorite(id: String) = savedDao.delete(id)
    suspend fun isFavorite(id: String) = savedDao.isSaved(id)

    // Thư viện
    suspend fun getLibrary(page: Int, pageSize: Int) =
        savedDao.getLibrary(limit = pageSize, offset = page * pageSize).map { it.toDomain() }

    // Chi tiết: chỉ DB
    suspend fun getDetail(id: String) =
        bookDao.getById(id)?.toDomain()
}
data class SearchResult(val items: List<Book>, val fromOffline: Boolean)
