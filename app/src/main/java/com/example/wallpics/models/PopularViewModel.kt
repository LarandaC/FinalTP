package com.example.wallpics.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpics.constans.UiState
import com.example.wallpics.data.repo.WallpaperRepository
import com.example.wallpics.data.retrofitService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularWallpapersViewModel : ViewModel() {
    var imageList = mutableStateOf<List<WallpaperModel>>(emptyList()) // La lista de wallpapers
        private set
    var currentPage: Int =  1
    var lastPage: Int = 1

    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set


    fun fetchPopularWallpapers(purity: Int, page: Int) {
        viewModelScope.launch {
            uiState = try {
                UiState.Loading
                val response = retrofitService.webService.getPopularWallpapers(purity = purity, page = page)
                if (response.isSuccessful) {
                    val wallpapersResponse = response.body()
                    println("popular response: $wallpapersResponse")
                    if (wallpapersResponse != null) {
                        imageList.value += wallpapersResponse.resultados
                        currentPage = wallpapersResponse.meta.currentPage
                        lastPage = wallpapersResponse.meta.lastPage
                         UiState.Success
                    } else {
                        println("La respuesta está vacía o nula.")
                         UiState.Empty
                    }
                } else {
                    println("Error en la respuesta: ${response.message()}")
                     UiState.Error
                }
            } catch (e: Exception) {
                println("Error al cargar las imágenes: ${e.message}")
                 UiState.Error
            }
        }
    }
}