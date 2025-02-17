package com.example.mangashelf.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mangashelf.network.models.Manga

@Composable
fun MangaList(
    modifier: Modifier = Modifier,
    state: LazyListState? = null,
    mangaList: List<Manga>,
    onItemClick: (String) -> Unit,
    onUpdateReadStatus: (String, Boolean) -> Unit,
) {
    val listState = state ?: rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .then(modifier),
        state = listState,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items = mangaList, key = { it.id }) { manga ->
            MangaListItemView(manga = manga, onItemClick = onItemClick) {
                onUpdateReadStatus(manga.id, it)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MangaListPreview() {
    MangaList(
        mangaList = listOf(
            Manga(
                id = "16787",
                title = "Manga Title",
                publishedChapterDate = 1343258805,
            )
        ),
        onItemClick = {}
    ) { _, _ ->
    }
}