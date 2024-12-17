package com.example.wallpics.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.wallpics.models.WallpaperModel
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WallpaperItem(wallpaper: WallpaperModel, isFavorite: Boolean,
                  onClick: (WallpaperModel) -> Unit,
                  onDoubleClick: (WallpaperModel) -> Unit,
                  onRemoveFavorite: (WallpaperModel) -> Unit) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick(wallpaper) },
                onDoubleClick = { onDoubleClick(wallpaper) }
            ),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(wallpaper.thumbs.original),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(wallpaper.ratio.toFloat())
            )
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.Red,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clickable { onRemoveFavorite(wallpaper) }
                )
            }
        }
    }
}
