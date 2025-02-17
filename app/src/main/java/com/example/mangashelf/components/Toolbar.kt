package com.example.mangashelf.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mangashelf.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaShelfToolbar(
    tabs: List<String> = listOf(),
    selectedTabIndex: Int = 0,
    onTabSelected: (Int) -> Unit = {},
) {
    Column {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
        if (tabs.isNotEmpty()) {
            TabView(selectedTabIndex, tabs) {
                onTabSelected(it)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun BookShelfToolbarPreview() {
    MangaShelfToolbar()
}


@Preview(showBackground = true)
@Composable
private fun BookShelfToolbarWithTabsPreview() {
    MangaShelfToolbar(tabs = listOf("2021", "2022", "2023", "2024", "2025"))
}