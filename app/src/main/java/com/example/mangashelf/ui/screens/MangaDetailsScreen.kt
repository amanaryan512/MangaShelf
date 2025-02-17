package com.example.mangashelf.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mangashelf.R
import com.example.mangashelf.common.CommonUtil
import com.example.mangashelf.common.CommonUtil.getRatingScore
import com.example.mangashelf.network.models.Manga
import com.example.mangashelf.viewmodel.MangaViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaDetailsScreen(
    manga: Manga,
    mangaViewModel: MangaViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val fav = remember {
        mutableStateOf(manga.isRead)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),

                title = { Text(text = stringResource(id = R.string.manga_details)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            manga.image?.let {
                AsyncImage(
                    model = it,
                    contentDescription = manga.title,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.FillBounds,
                    placeholder = painterResource(id = R.drawable.image_placeholder)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = manga.title ?: "Unknown Title",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Star, contentDescription = "Rating", tint = colorResource(
                        id = android.R.color.holo_green_light
                    )
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = String.format(Locale.US, "%.1f", manga.getRatingScore()),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    R.string.published_in,
                    CommonUtil.getYearFromTimeStamp(manga.publishedChapterDate ?: 0L)
                ),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Category: ${manga.category ?: "Unknown"}",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(
                    R.string.popularity,
                    CommonUtil.formatPopularity(manga.popularity ?: 0L)
                ),
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            // Favorite button
            IconButton(onClick = {
                fav.value = !fav.value
                mangaViewModel.updateReadStatus(manga.id, fav.value)
            }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = if (fav.value) {
                        painterResource(id = R.drawable.ic_favourite_filled)
                    } else {
                        painterResource(id = R.drawable.ic_favourite_outlined)
                    },
                    tint = colorResource(id = android.R.color.holo_red_dark),
                    contentDescription = null
                )
            }
        }
    }
}
