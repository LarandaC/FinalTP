package com.example.wallpics.ui

import FavoritesScreen
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wallpics.data.DatabaseProvider
import com.example.wallpics.models.AuthViewModel
import com.example.wallpics.models.DownloadViewModel
import com.example.wallpics.models.DownloadsViewModelFactory
import com.example.wallpics.models.FavoritesViewModel
import com.example.wallpics.models.FavoritesViewModelFactory
import com.example.wallpics.models.WallpaperViewModel
import com.example.wallpics.ui.screens.Download
import com.example.wallpics.ui.screens.LoginScreen
import com.example.wallpics.ui.screens.ProfileScreen
import com.example.wallpics.ui.screens.RegisterScreen
import com.example.wallpics.ui.screens.Search
import com.example.wallpics.ui.screens.WallpaperScreen
import com.example.wallpics.ui.screens.WallpaperView
import com.example.wallpics.ui.theme.DarkColorScheme
import com.example.wallpics.ui.theme.LightColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpicsApp( modifier: Modifier = Modifier, viewModel: WallpicsViewModel = viewModel()) {
    val navController = rememberNavController()

    val wallpaperViewModel: WallpaperViewModel = viewModel() // Instancia del ViewModel

    val scrollBehavior =
        TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val isDarkTheme = isSystemInDarkTheme()

    val context = LocalContext.current
    val favoritesDao = DatabaseProvider.getDatabase(context).favoritesDao()
    val authViewModel: AuthViewModel = viewModel()
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(favoritesDao)
    )

    val downloadDao = DatabaseProvider.getDatabase(context).downloadDao()
    val downloadViewModel: DownloadViewModel = viewModel(
        factory = DownloadsViewModelFactory(downloadDao)
    )

    MaterialTheme(
        colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme
    ) {

        Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {TopBar(navController, viewModel, scrollBehavior)},
                bottomBar = { BottomNavigation(navController, viewModel) }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Route.Home,
                    Modifier.padding(innerPadding),
                ) {

                    composable<Route.Login> {
                        LoginScreen(navController, authViewModel)
                    }
                    composable<Route.Register> {
                        RegisterScreen(navController, authViewModel)
                    }

                    composable<Route.Home> {
                        WallpaperScreen(wallpaperViewModel, navController, favoritesViewModel)
                    }
                    composable<Route.Favorites> {
                        FavoritesScreen(
                            favoritesDao,
                            navController,
                            onWallpaperClick = {}
                        )
                    }
                    composable<Route.Profile> {
                        ProfileScreen(wallpaperViewModel, navController)
                    }
                    composable<Route.WallpaperView>{
                        WallpaperView(wallpaperViewModel, scrollBehavior, downloadViewModel, favoritesViewModel)
                    }
                    composable<Route.Search>{
                        Search(navController = navController, mainViewModel = wallpaperViewModel, favoritesViewModel = favoritesViewModel)
                    }
                    composable<Route.Download> {
                        Download(downloadDao, navController, onWallpaperClick = {})
                    }
                }
            }
    }
}

