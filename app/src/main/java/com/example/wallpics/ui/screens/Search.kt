package com.example.wallpics.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.wallpics.models.SearchViewModel
import com.example.wallpics.models.WallpaperViewModel
import com.example.wallpics.ui.Route
import com.example.wallpics.ui.WallpicsViewModel
import com.example.wallpics.ui.components.WallpaperGrid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    viewModel: SearchViewModel = viewModel(),
    navController: NavController?,
    mainViewModel: WallpaperViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val wallpaperList = viewModel.imageList.value
    val keyboardController = LocalSoftwareKeyboardController.current

        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {
                if(wallpaperList.isNotEmpty()) {
                    viewModel.imageList.value = emptyList()

                }
                viewModel.lastPage = 1
                viewModel.currentPage = 1
                viewModel.searchByQuery(query = it, purity = 100, page = 1)
                keyboardController?.hide()
            },
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            active = true,
            onActiveChange = {},
            tonalElevation = 0.dp,
            content = {
                if (wallpaperList.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "No hay nada que mostrar",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Intenta con una nueva busqueda",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    WallpaperGrid(
                        wallpaperList,
                        {
                            mainViewModel.selectWallpaper(it)
                            navController?.navigate(Route.WallpaperView)
                        },
                        onBottomReached = {

                             viewModel.searchByQuery(query = searchQuery, purity = 100, page = ++viewModel.currentPage)
                        }
                    )

                }
            },


        )
}
