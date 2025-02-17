package com.example.mangashelf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mangashelf.common.CommonUtil
import com.example.mangashelf.common.DataState
import com.example.mangashelf.network.models.Manga
import com.example.mangashelf.usecase.MangaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(private val useCase: MangaUseCase) : ViewModel() {

    private var _originalMangaList: List<Manga> = emptyList() // Stores the unfiltered list

    private var _mangaListUiState = MutableStateFlow<MangaListUiState>(MangaListUiState.Loading)
    val mangaListUiState = _mangaListUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _sortType = MutableStateFlow(SortType.NONE)
    val sortType = _sortType.asStateFlow()

    fun getMangaList(noCacheData: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            if (noCacheData) {
                _isRefreshing.value = true
            }
            useCase.getAllMangas(noCacheData).collect {
                _mangaListUiState.value = when (it) {
                    is DataState.Loading -> MangaListUiState.Loading

                    is DataState.Error -> {
                        _isRefreshing.value = false
                        MangaListUiState.Error(it.errorMessage)
                    }
                    is DataState.Success -> {
                        _isRefreshing.value = false
                        val responseList = it.response.orEmpty().sortedBy {timestamp ->  timestamp.publishedChapterDate } // sort in ascending order according to year
                        val tabs =  CommonUtil.prepareTabs(it.response.orEmpty())
                        setMangaList(responseList, tabs)
                        MangaListUiState.Success(responseList, tabs)
                    }
                }
            }
        }
    }

    fun getIndexPosition(selectedYear: String): Int {
        if (_mangaListUiState.value is MangaListUiState.Success) {
            val mangaList = (_mangaListUiState.value as MangaListUiState.Success).mangaList

            // Find the index of the first item with the selected year
            return mangaList.indexOfFirst {
                CommonUtil.getYearFromTimeStamp(it.publishedChapterDate ?: 0) == selectedYear
            }

        }
        return -1
    }


    fun updateReadStatus(id: String, isRead: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateReadStatus(id, isRead)
        }
    }

     private fun setMangaList(mangaList: List<Manga>, tabs: List<String>) {
        _originalMangaList = mangaList // Store the original list
        _mangaListUiState.value = MangaListUiState.Success(mangaList, tabs)
    }

    fun updateSortType(sortType: SortType) {
        _sortType.value = sortType
        val currentState = _mangaListUiState.value
        if (currentState is MangaListUiState.Success) {
            val updatedList = if (sortType != SortType.NONE) {
                sortMangaList(_originalMangaList, sortType)
            } else {
                _originalMangaList // Reset to the original list
            }
            _mangaListUiState.value = MangaListUiState.Success(updatedList, currentState.tabs)
        }
    }

    private fun sortMangaList(mangaList: List<Manga>, sortBy: SortType): List<Manga> {
        return when (sortBy) {
            SortType.SCORE -> mangaList.sortedByDescending { it.score }
            SortType.POPULARITY -> mangaList.sortedByDescending { it.popularity }
            SortType.NONE -> mangaList // No sorting applied
        }
    }
}

sealed class MangaListUiState {
    data object Loading : MangaListUiState()
    data class Success(val mangaList: List<Manga>, val tabs: List<String>) : MangaListUiState()
    data class Error(val errorMessage: String) : MangaListUiState()
}

enum class SortType {
    SCORE,
    POPULARITY,
    NONE
}

