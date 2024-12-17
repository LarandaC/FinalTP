package com.example.wallpics.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun fetchPopularWallpapers(purity: Int, page: Int) {
        viewModelScope.launch {
            try {
                val response = retrofitService.webService.getPopularWallpapers(purity = purity, page = page)
                if (response.isSuccessful) {
                    val wallpapersResponse = response.body()
                    println("popular response: $wallpapersResponse")
                    if (wallpapersResponse != null) {
                        imageList.value += wallpapersResponse.resultados
                        currentPage = wallpapersResponse.meta.currentPage
                        lastPage = wallpapersResponse.meta.lastPage
                    } else {
                        println("La respuesta está vacía o nula.")
                    }
                } else {
                    println("Error en la respuesta: ${response.message()}")
                }
            } catch (e: Exception) {
                println("Error al cargar las imágenes: ${e.message}")
            }
        }
    }
}