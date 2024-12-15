package com.example.wallpics.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wallpics.models.FavoritesViewModel
import com.example.wallpics.models.PopularWallpapersViewModel
import com.example.wallpics.models.WallpaperModel
import com.example.wallpics.models.toEntity
import com.example.wallpics.models.toModel
import com.example.wallpics.ui.components.WallpaperItem

@Composable
fun PopularWallpapersScreen(
    navController: NavController,
    onWallpaperClick: (WallpaperModel) -> Unit
) {
    // Obtenemos el ViewModel
    val viewModel: PopularWallpapersViewModel = viewModel()

    // Observamos el StateFlow de los wallpapers populares
    val wallpapers = viewModel.popularWallpapers.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val favoritesViewModel: FavoritesViewModel = viewModel()

    // Cargamos los wallpapers al iniciar la pantalla (podrías llamar esto de alguna manera, como en un evento OnStart)
    viewModel.loadPopularWallpapers()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (!errorMessage.isNullOrEmpty()) {
            // Muestra el mensaje de error si existe
            Text("Error: $errorMessage")
        } else if (wallpapers.isEmpty()) {
            Text("No hay wallpapers populares disponibles.")
        } else {
            // Muestra los wallpapers
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 4.dp,
                modifier = Modifier.fillMaxSize()
            ) {
                items(wallpapers) { wallpaperModel ->
                    WallpaperItem(
                        wallpaper = wallpaperModel,  // Asegúrate de que wallpaperModel sea el tipo correcto
                        onClick = { onWallpaperClick(wallpaperModel) },
                        onDoubleClick = {
                            // Convertimos el WallpaperModel a WallpaperEntity para guardarlo en favoritos
                            val wallpaperEntity =
                                wallpaperModel.toEntity()  // Asumiendo que tienes este metodo de conversión
                            favoritesViewModel.addFavorite(wallpaperEntity)
                        }
                    )
                }
            }
        }
    }
}