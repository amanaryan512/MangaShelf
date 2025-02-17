package com.example.mangashelf.network.models

import com.google.gson.annotations.SerializedName

data class Manga(
    val id: String,
    val image: String? = null,
    val score: Double? = null,
    val popularity: Long? = null,
    val title: String? = null,
    @SerializedName("publishedChapterDate")
    val publishedChapterDate: Long? = null,
    val category: String? = null,
    val isRead: Boolean = false,
)
