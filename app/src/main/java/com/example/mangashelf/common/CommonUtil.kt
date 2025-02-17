package com.example.mangashelf.common

import com.example.mangashelf.network.models.Manga
import com.example.mangashelf.room.entities.MangaEntity
import java.util.Calendar
import kotlin.math.roundToInt

object CommonUtil {
    fun Manga.mangaEntity(): MangaEntity {
        return MangaEntity(
            id = id,
            image = image,
            score = score,
            popularity = popularity,
            title = title,
            publishedChapterDate = publishedChapterDate,
            category = category,
            isRead = isRead
        )
    }

    fun MangaEntity.manga(): Manga {
        return Manga(
            id = id,
            image = image,
            score = score,
            popularity = popularity,
            title = title,
            publishedChapterDate = publishedChapterDate,
            category = category,
            isRead = isRead
        )
    }

    fun getYearFromTimeStamp(publishedChapterDate: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = publishedChapterDate * 1000 // convert into millis
        return calendar.get(Calendar.YEAR).toString()
    }

    fun prepareTabs(bookList: List<Manga>): List<String> {
        return bookList.asSequence().mapNotNull { it.publishedChapterDate?.let { timestamp ->
            if (timestamp > 0) getYearFromTimeStamp(timestamp) else null
        } }
            .distinct()
            .map { it.toInt() } // Convert to Int for correct sorting
            .sorted()
            .map { it.toString() }.toList() // Convert back to String
    }

    fun Manga.getRatingScore() : Double {
        val ratingScore = score?.div(10) ?: 0.0
        if(ratingScore >= 0 && ratingScore < 1) {
            // So if rating score is between 0 and 1, we will round it off
            return ratingScore.roundToInt().toDouble()
        }
        return ratingScore
    }

    fun formatPopularity(popularity: Long): String {
        return when {
            popularity >= 1_000_000 -> "${popularity / 1_000_000}M" // Convert to M (Millions)
            popularity >= 1_000 -> "${popularity / 1_000}K" // Convert to K (Thousands)
            else -> popularity.toString()
        }
    }
}