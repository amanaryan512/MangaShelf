package com.example.mangashelf.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mangashelf.room.dao.MangaDao
import com.example.mangashelf.room.entities.MangaEntity

@Database(entities = [MangaEntity::class], version = 1)
abstract class MangaDatabase: RoomDatabase() {
    abstract fun mangaDao(): MangaDao
}