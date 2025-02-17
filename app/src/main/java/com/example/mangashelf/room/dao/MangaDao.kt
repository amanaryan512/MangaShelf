package com.example.mangashelf.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mangashelf.room.entities.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(mangas: List<MangaEntity>)

    @Query("SELECT * FROM mangas ORDER BY publishedChapterDate DESC")
    fun getMangas(): Flow<List<MangaEntity>>

    @Query("DELETE FROM mangas WHERE id NOT IN (:ids)")
    suspend fun deleteNotInList(ids: List<String>)

    @Query("UPDATE mangas SET isRead = :isFavourite WHERE id = :id")
    suspend fun updateManga(id: String, isFavourite: Boolean)
}