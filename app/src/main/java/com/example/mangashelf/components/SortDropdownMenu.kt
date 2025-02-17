package com.example.mangashelf.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mangashelf.R
import com.example.mangashelf.viewmodel.SortType

@Composable
fun SortDropdownMenu(selectedSort: SortType, onSortSelected: (SortType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Button(onClick = { expanded = true }) {
            Text(
                text = stringResource(
                    id = R.string.sort_by, if (selectedSort == SortType.NONE) stringResource(
                        id = R.string.none
                    ) else selectedSort.name
                )
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text(stringResource(id = R.string.score)) }, onClick = {
                onSortSelected(SortType.SCORE)
                expanded = false
            })
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.popularity_text)) },
                onClick = {
                    onSortSelected(SortType.POPULARITY)
                    expanded = false
                })
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text(stringResource(id = R.string.reset_filter)) },
                onClick = {
                    onSortSelected(SortType.NONE)
                    expanded = false
                })
        }
    }
}


