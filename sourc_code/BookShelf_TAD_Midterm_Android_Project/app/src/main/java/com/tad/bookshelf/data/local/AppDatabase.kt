package com.tad.bookshelf.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tad.bookshelf.data.local.dao.BookDao
import com.tad.bookshelf.data.local.dao.SavedBookDao
import com.tad.bookshelf.data.local.entities.BookEntity
import com.tad.bookshelf.data.local.entities.SavedBookEntity
import com.tad.bookshelf.domain.model.Book

@Database(entities = [BookEntity::class, SavedBookEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun savedDao(): SavedBookDao
    abstract fun bookDao(): BookDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase
        = INSTANCE ?:
        synchronized(this){
            Room.databaseBuilder(context , AppDatabase::class.java, "bookshelf.db")
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}