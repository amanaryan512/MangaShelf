package com.example.mangashelf.network.repository

import com.example.mangashelf.common.DataState
import com.example.mangashelf.network.models.Manga
import kotlinx.coroutines.flow.Flow

interface MangaRepository {
    suspend fun getAllMangas(noCacheData: Boolean): Flow<DataState<List<Manga>?>>
    suspend fun updateReadStatus(id: String, isRead: Boolean)
}