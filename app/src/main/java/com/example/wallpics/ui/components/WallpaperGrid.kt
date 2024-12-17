package com.example.wallpics.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.wallpics.models.WallpaperModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun WallpaperGrid (
    listaWallpappers: List<WallpaperModel>,
    favoriteIds: Set<String>,
    onWallpaperClick: (WallpaperModel) -> Unit,
    onWallpaperDoubleClick: (WallpaperModel) -> Unit = {},
    onRemoveFavorite: (WallpaperModel) -> Unit,
    modifier: Modifier = Modifier,
    loadingContent: @Composable () -> Unit = {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    },
    onBottomReached: () -> Unit
) {

    val listState = rememberLazyStaggeredGridState()

    val bottom: Boolean by remember {
        derivedStateOf{
            val lastItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(bottom) {
        if (bottom) onBottomReached()
    }


    if (listaWallpappers.isEmpty()) {
        loadingContent()
    } else {
        LazyVerticalStaggeredGrid(
            state = listState,
            columns = StaggeredGridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
            modifier = modifier
        ) {
            items(listaWallpappers) { wallpaper ->
                WallpaperItem(
                    wallpaper = wallpaper,
                    isFavorite = favoriteIds.contains(wallpaper.id),
                    onClick = { onWallpaperClick(wallpaper) },
                    onDoubleClick = { onWallpaperDoubleClick(wallpaper) },
                    onRemoveFavorite = { onRemoveFavorite(wallpaper) }
                )
            }
        }
    }
}
