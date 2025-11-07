package com.tad.bookshelf.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "saved_books",
    primaryKeys = ["bookId"],
    indices = [Index("bookId")],
    foreignKeys = [ForeignKey(
        entity = BookEntity::class,
        parentColumns = ["id"],
        childColumns = ["bookId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SavedBookEntity(
    val bookId: String,
    val createdAt: Long = System.currentTimeMillis()
)