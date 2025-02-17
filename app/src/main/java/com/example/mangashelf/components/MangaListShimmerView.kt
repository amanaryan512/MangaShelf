package com.example.mangashelf.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun MangaListShimmerView() {
    ListItem(
        leadingContent = {
            Box(
                Modifier
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1.0f)
                    .background(Color.Gray, shape = RoundedCornerShape(12.dp))

            )
        },
        headlineContent = {
            Box(
                Modifier
                    .padding(vertical = 8.dp)
                    .width(100.dp)
                    .height(8.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(6.dp))
            )
        },
        overlineContent = {
            Box(
                Modifier
                    .padding(vertical = 4.dp)
                    .width(150.dp)
                    .height(16.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(6.dp))
            )
        },
        supportingContent = {
            Box(
                Modifier
                    .width(70.dp)
                    .height(8.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(6.dp))
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color.White
        ),
        modifier = Modifier.shimmer()
    )
}

@Preview(showBackground = true)
@Composable
private fun MangaListShimmerViewPreview() {
    MangaListShimmerView()
}