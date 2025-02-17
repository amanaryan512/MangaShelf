package com.example.mangashelf.room.di

import android.content.Context
import androidx.room.Room
import com.example.mangashelf.room.dao.MangaDao
import com.example.mangashelf.room.database.MangaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): MangaDatabase {
        return Room.databaseBuilder(
            context,
            MangaDatabase::class.java,
            "manga-database")
            .build()
    }

    @Singleton
    @Provides
    fun providesMangaDao(mangaDatabase: MangaDatabase): MangaDao {
        return mangaDatabase.mangaDao()
    }
}