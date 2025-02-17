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

    private var _mangaListUiState = MutableStateFlow<MangaListUiState>(MangaListUiState.Loading)
    val mangaListUiState = _mangaListUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

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
                        MangaListUiState.Success(
                            it.response.orEmpty().sortedBy {timestamp ->  timestamp.publishedChapterDate }, // sort in ascending order according to year
                            CommonUtil.prepareTabs(it.response.orEmpty())
                        )
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
}

sealed class MangaListUiState {
    data object Loading : MangaListUiState()
    data class Success(val mangaList: List<Manga>, val tabs: List<String>) : MangaListUiState()
    data class Error(val errorMessage: String) : MangaListUiState()
}