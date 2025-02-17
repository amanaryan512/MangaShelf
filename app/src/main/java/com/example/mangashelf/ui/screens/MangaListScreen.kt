package com.example.mangashelf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mangashelf.common.CommonUtil
import com.example.mangashelf.components.ErrorView
import com.example.mangashelf.components.MangaList
import com.example.mangashelf.components.MangaListShimmerView
import com.example.mangashelf.components.MangaShelfToolbar
import com.example.mangashelf.viewmodel.MangaListUiState
import com.example.mangashelf.viewmodel.MangaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaListScreen(
    modifier: Modifier = Modifier,
    mangaListViewModel: MangaViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val firstVisibleItem by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex }
    }

    val mangaListState by mangaListViewModel.mangaListUiState.collectAsStateWithLifecycle()
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var tabs by remember {
        mutableStateOf(listOf<String>())
    }

    val sortType by mangaListViewModel.sortType.collectAsStateWithLifecycle()

    // Call the Manga Shelf API on screen Launch.
    LaunchedEffect(key1 = Unit) {
        mangaListViewModel.getMangaList()
    }

    Scaffold(
        topBar = {
            MangaShelfToolbar(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex
            ) { tabIndex ->
                // Scroll to the selected year when user clicks on the tab
                coroutineScope.launch {
                    val selectedYear = tabs[tabIndex]
                    val scrollToIndex = mangaListViewModel.getIndexPosition(selectedYear)
                    if (scrollToIndex != -1) {
                        lazyListState.scrollToItem(scrollToIndex)
                    }
                }
            }
        }
    ) { padding ->

        val isRefreshing by mangaListViewModel.isRefreshing.collectAsStateWithLifecycle()
        val pullRefreshState = rememberPullToRefreshState()

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            state = pullRefreshState,
            onRefresh = {
                mangaListViewModel.getMangaList(noCacheData = true)
            },
            modifier = Modifier
                .background(Color.White)
                .padding(padding)
                .fillMaxSize()
                .then(modifier)
        ) {
            when (val state = mangaListState) {
                is MangaListUiState.Error -> {
                    ErrorView(state.errorMessage)
                }

                MangaListUiState.Loading -> {
                    Column {
                        repeat(10) {
                            MangaListShimmerView()
                        }
                    }
                }

                is MangaListUiState.Success -> {
                    val mangaList = state.mangaList
                    tabs = state.tabs

                    LaunchedEffect(firstVisibleItem) {
                        // Select tab based on firstVisibleItem (Manga Year)
                        selectedTabIndex = tabs.indexOf(mangaList[firstVisibleItem]
                            .publishedChapterDate?.let { timestamp ->
                                CommonUtil.getYearFromTimeStamp(timestamp)
                            })
                    }

                    MangaList(
                        mangaList = mangaList,
                        onItemClick = onItemClick,
                        sortType = sortType,
                        onSortSelected = {
                            mangaListViewModel.updateSortType(it)
                        },
                        state = lazyListState
                    ) { id, isRead ->
                        mangaListViewModel.updateReadStatus(id, isRead)
                    }
                }
            }
        }
    }
}