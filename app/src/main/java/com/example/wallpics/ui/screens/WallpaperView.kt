package com.example.wallpics.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wallpics.models.WallpaperViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.wallpics.models.DownloadViewModel
import com.example.wallpics.models.FavoritesViewModel
import com.example.wallpics.models.toDownloadEntity
import com.example.wallpics.models.toEntity
import com.example.wallpics.ui.components.ExpandableFAB
import com.example.wallpics.ui.components.FABItem
import com.example.wallpics.ui.theme.BarraFondoDark
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.utils.AndroidDownloader
import com.example.wallpics.utils.showToast

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperView(
    wallpaperViewModel: WallpaperViewModel = viewModel(),
    scrollBehavior: TopAppBarScrollBehavior,
    downloadViewModel: DownloadViewModel,
    favoriteViewModel: FavoritesViewModel
) {
    val context = LocalContext.current
    val downloader = remember { AndroidDownloader(context) }

    val favorites by favoriteViewModel.favorites.observeAsState(emptyList())
    val favoriteIds = favorites.map { it.id }.toSet()

    val isDarkTheme = isSystemInDarkTheme()

    scrollBehavior.state.heightOffset = 0f
    val picture = wallpaperViewModel.selectedWallpaper

    val scaleState = remember { mutableStateOf(1f) }

    if (picture == null) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            floatingActionButton = {
                val itemList = listOf(
                    FABItem(icon = Icons.Rounded.Favorite, text = "Agregar a favoritos"),
                    FABItem(icon = Icons.Rounded.Download, text = "Descargar"),
                )
                ExpandableFAB(
                    items = itemList,
                    onItemClick = { item ->
                        when (item.text) {
                            "Agregar a favoritos" -> {
                                if (favoriteIds.contains(picture.id)) {
                                    showToast(context, "Ya se encuentra en favoritos")
                                } else {
                                    favoriteViewModel.addFavorite(picture.toEntity())
                                    showToast(context, "Agregado a favoritos")
                                }
                            }

                            "Descargar" -> {
                                downloader.downloadFile(picture.path, picture.id)
                                downloadViewModel.addDownload(picture.toDownloadEntity())
                                showToast(context, "Descarga exitosa del archivo")
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Da más peso al wallpaper
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(picture.path),
                        contentDescription = picture.category,
                        modifier = Modifier
                            .fillMaxSize() // Asegura que la imagen ocupe el ancho completo
                            .aspectRatio(1f) // Mantiene la relación de aspecto de la imagen
                            .graphicsLayer(
                                scaleX = scaleState.value,
                                scaleY = scaleState.value
                            )
                            .pointerInput(Unit) {
                                detectTransformGestures { _, _, zoom, _ ->
                                    // Limita el zoom para no hacerlo más pequeño que el tamaño original
                                    scaleState.value = (scaleState.value * zoom).coerceIn(1f, 3f)
                                }
                            }
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Imagen del uploader
                    picture.uploader?.avatar?.medium?.let { uploaderImageUrl ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(uploaderImageUrl),
                                contentDescription = "Uploader Image",
                                modifier = Modifier
                                    .clip(CircleShape) // Recorta en forma de círculo
                            )
                            Text(
                                text = picture.uploader.username ?: "N/A",
                                modifier = Modifier.padding(start = 8.dp) // Espaciado entre la imagen y el texto
                            )
                        }
                    }
                    Text(
                        text = "Category: ${picture.category ?: "N/A"}",
                        modifier = Modifier.padding(top = 8.dp) // Espaciado hacia abajo
                    )
                }
            }
        }
    }
}
