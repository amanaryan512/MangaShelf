package com.example.mangashelf.usecase

import com.example.mangashelf.common.DataState
import com.example.mangashelf.network.models.Manga
import com.example.mangashelf.network.repository.MangaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MangaUseCase @Inject constructor(private val mangaRepository: MangaRepository) {

    suspend fun getAllMangas(noCacheData: Boolean): Flow<DataState<List<Manga>?>> =
        mangaRepository.getAllMangas(noCacheData)

    suspend fun updateReadStatus(id: String, isRead: Boolean) = mangaRepository.updateReadStatus(id, isRead)
}