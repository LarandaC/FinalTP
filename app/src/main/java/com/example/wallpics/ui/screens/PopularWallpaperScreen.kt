package com.example.wallpics.ui.screens

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wallpics.models.FavoritesViewModel
import com.example.wallpics.models.PopularWallpapersViewModel
import com.example.wallpics.models.WallpaperModel
import com.example.wallpics.models.WallpaperViewModel
import com.example.wallpics.models.toEntity
import com.example.wallpics.models.toModel
import com.example.wallpics.ui.Route
import com.example.wallpics.ui.components.WallpaperGrid
import com.example.wallpics.ui.components.WallpaperItem
import com.example.wallpics.ui.theme.BarraFondoDark
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.ui.theme.LightColorScheme

@Composable
fun PopularWallpapersScreen(
    viewModel: PopularWallpapersViewModel = viewModel(),
    navController: NavController,
    wallpaperViewModel: WallpaperViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel
) {
    // Observamos el StateFlow de los wallpapers populares
    val listaWallpappers = viewModel.imageList.value
    val isDarkTheme = isSystemInDarkTheme()
    val favorites by favoritesViewModel.favorites.observeAsState(emptyList())
    val favoriteIds = favorites.map { it.id }.toSet()

    LaunchedEffect(Unit) {
        viewModel.fetchPopularWallpapers(purity = 100, page = viewModel.currentPage)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Card(
            modifier = Modifier
                .padding(top = 20.dp, bottom = 20.dp)
                .wrapContentSize() // Ajusta el tamaño de la tarjeta al contenido
                .padding(horizontal = 30.dp), // Ajusta el padding según sea necesario
            shape = RoundedCornerShape(50), // Hace que la tarjeta sea ovalada
            colors = CardDefaults.cardColors(
                containerColor = if (isDarkTheme) DarkColorScheme.primary else BarraFondoDark
            ),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            Text(
                text = "Populares de la semana",
                modifier = Modifier
                    .padding(10.dp),
                fontWeight = FontWeight.SemiBold,
                color = if (isDarkTheme) LightColorScheme.onPrimary else DarkColorScheme.background,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        WallpaperGrid(
            listaWallpappers,
            favoriteIds = favoriteIds,
            {
                wallpaperViewModel.selectWallpaper(it)
                navController.navigate(Route.WallpaperView)
            },
            onWallpaperDoubleClick = { wallpaper ->
                if (favoriteIds.contains(wallpaper.id)) {
                    favoritesViewModel.removeFavorite(wallpaper.toEntity())
                } else {
                    favoritesViewModel.addFavorite(wallpaper.toEntity())
                }
            },
            onRemoveFavorite = { wallpaper ->
                favoritesViewModel.removeFavorite(wallpaper.toEntity())
            },
            onBottomReached = {
                viewModel.fetchPopularWallpapers(
                    purity = 100,
                    page = ++viewModel.currentPage
                )
            }
        )
    }
}