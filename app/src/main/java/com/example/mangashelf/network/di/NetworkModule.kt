package com.example.mangashelf.network.di

import com.example.mangashelf.network.MangaShelfApiService
import com.example.mangashelf.network.repository.MangaRepository
import com.example.mangashelf.network.repository.MangaRepositoryImpl
import com.example.mangashelf.room.dao.MangaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://www.jsonkeeper.com/"

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesMangaApiService(retrofit: Retrofit): MangaShelfApiService {
        return retrofit.create(MangaShelfApiService::class.java)
    }

    @Provides
    fun providesMangaRepository(mangaShelfApiService: MangaShelfApiService, mangaDao: MangaDao): MangaRepository {
        return MangaRepositoryImpl(mangaShelfApiService, mangaDao)
    }
}