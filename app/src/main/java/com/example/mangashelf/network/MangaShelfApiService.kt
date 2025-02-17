package com.example.mangashelf.network

import com.example.mangashelf.network.models.Manga
import retrofit2.Response
import retrofit2.http.GET

interface MangaShelfApiService {

    @GET("b/KEJO")
    suspend fun getMangaList(): Response<List<Manga>?>
}