package com.example.mangashelf.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mangashelf.R
import com.example.mangashelf.common.CommonUtil
import com.example.mangashelf.common.CommonUtil.getRatingScore
import com.example.mangashelf.network.models.Manga
import com.google.gson.Gson
import java.util.Locale

@Composable
fun MangaListItemView(manga: Manga, onItemClick: (String) -> Unit = {},  onClickFavourite: (Boolean) -> Unit = {}) {
    val fav = remember {
        mutableStateOf(manga.isRead)
    }

    ListItem(
        modifier = Modifier.clickable {
            onItemClick.invoke(Gson().toJson(manga))
        },
        leadingContent = {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1.0f)
                    .clip(RoundedCornerShape(12.dp)),

                model = manga.image.toString(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.image_placeholder)
            )
        },
        headlineContent = {
            Text(
                text = manga.title.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
        },
        supportingContent = {
            Column {
                manga.publishedChapterDate?.let {
                    Text(
                        stringResource(R.string.published_in, CommonUtil.getYearFromTimeStamp(it)),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                manga.category?.let {
                    Text(
                        stringResource(R.string.category, it),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                manga.popularity?.let {
                    Text(
                        stringResource(R.string.popularity, CommonUtil.formatPopularity(it)),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                 // Score - rating
                Row(
                    Modifier
                        .padding(vertical = 4.dp)
                        .background(
                            color = colorResource(id = android.R.color.holo_green_light).copy(
                                alpha = 0.1f
                            ), shape = RoundedCornerShape(8.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp),
                        imageVector = Icons.Rounded.Star,
                        contentDescription = null,
                        tint = colorResource(
                            id = android.R.color.holo_green_light
                        )
                    )
                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = String.format(Locale.US, "%.1f", manga.getRatingScore()),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = android.R.color.holo_green_light),
                    )
                }
            }
        },
        trailingContent = {
            // Favorite Icon
            IconButton(onClick = { onClickFavourite(!fav.value) }) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    painter = if (fav.value) {
                        painterResource(id = R.drawable.ic_favourite_filled)
                    } else {
                        painterResource(id = R.drawable.ic_favourite_outlined)
                    },
                    tint = colorResource(id = android.R.color.holo_red_dark),
                    contentDescription = null
                )
            }

        },
        colors = ListItemDefaults.colors(
            containerColor = Color.White,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MangaListItemViewPreview() {
    MangaListItemView(
        manga = Manga(
            id = "1",
            title = "Manga Title",
            popularity = 4,
            publishedChapterDate = 1343258805,
            isRead = false
        )
    )
}

@Preview(showBackground = true)
@Composable
fun MangaListItemViewFavouritePreview() {
    MangaListItemView(
        manga = Manga(
            id = "1",
            title = "Manga Title",
            score = 40.0,
            image = "https://cdn.myanimelist.net/images/anime/1161/136691.jpg",
            publishedChapterDate = 1343258805,
            isRead = true
        )
    )
}