package com.example.mangashelf.network.repository

import com.example.mangashelf.common.CommonUtil.manga
import com.example.mangashelf.common.CommonUtil.mangaEntity
import com.example.mangashelf.common.DataState
import com.example.mangashelf.network.MangaShelfApiService
import com.example.mangashelf.network.models.Manga
import com.example.mangashelf.network.safeApiCall
import com.example.mangashelf.room.dao.MangaDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MangaRepositoryImpl @Inject constructor(
    private val mangaShelfApiService: MangaShelfApiService,
    private val mangaDao: MangaDao
) : MangaRepository {

    override suspend fun getAllMangas(noCacheData: Boolean): Flow<DataState<List<Manga>?>> = flow {
        val fetchAndCacheMangas: suspend () -> Unit = {
            getMangaShelfFromNetwork().collect { networkDataState ->
                if (networkDataState is DataState.Success) {
                    val list = networkDataState.response.orEmpty()
                    val ids = list.map { it.id }

                    // Delete the mangas which are not in the list but are in the database and insert the new list
                    mangaDao.deleteNotInList(ids)
                    mangaDao.insertAll(list.map { it.mangaEntity() }) // Convert Manga Model class to Manga Entity to insert in database
                } else {
                    emit(networkDataState)
                }
            }
        }

        if (noCacheData) {
            // Fetch the data from the network and cache it in the database if noCacheData is true
            fetchAndCacheMangas()
        }

        mangaDao.getMangas().collect { localData ->
            if (localData.isEmpty()) {
                // Fetch the data from the network and cache it in the database if the database is empty
                fetchAndCacheMangas()
            } else {
                emit(DataState.Success(localData.map { it.manga() })) // convert Manga entity back to Manga model class.
            }
        }
    }

    override suspend fun updateReadStatus(id: String, isRead: Boolean) {
        mangaDao.updateManga(id, isRead)
    }


    private fun getMangaShelfFromNetwork(): Flow<DataState<List<Manga>?>> = flow {
        safeApiCall {
            mangaShelfApiService.getMangaList()
        }.collect { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    emit(DataState.Error(dataState.errorMessage))
                }

                DataState.Loading -> {
                    emit(DataState.Loading)
                }

                is DataState.Success -> {
                    emit(DataState.Success(dataState.response.orEmpty()))
                }
            }
        }
    }
}