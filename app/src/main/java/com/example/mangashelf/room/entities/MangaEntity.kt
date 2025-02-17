package com.example.mangashelf.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mangas")
data class MangaEntity(
    @PrimaryKey var id: String,
    val image: String? = null,
    val score: Double? = null,
    val popularity: Long? = null,
    val title: String? = null,
    val publishedChapterDate: Long? = null,
    val category: String? = null,
    val isRead: Boolean = false
)
