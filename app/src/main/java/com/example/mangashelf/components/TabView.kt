package com.example.mangashelf.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TabView(selectedTabIndex: Int, tabs: List<String>, onTabSelected: (Int) -> Unit = {}) {
    ScrollableTabRow(
        edgePadding = 0.dp,
        selectedTabIndex = selectedTabIndex
    ) {
        // Manga Years Tabs
        tabs.forEachIndexed { index, tabTitle ->
            Tab(
                modifier = Modifier.defaultMinSize(minHeight = 40.dp),
                unselectedContentColor = Color.Gray,
                selected = selectedTabIndex == index,
                onClick = {
                    onTabSelected(index)
                }
            ) {
                Text(
                    text = tabTitle,
                    style = if (selectedTabIndex == index)
                        MaterialTheme.typography.titleMedium
                    else
                        MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun YearTabViewPreview() {
    TabView(selectedTabIndex = 0, tabs = listOf("2021", "2022", "2023", "2024", "2025"))
}