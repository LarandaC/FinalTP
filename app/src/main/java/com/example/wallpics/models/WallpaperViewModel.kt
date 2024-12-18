package com.example.wallpics.models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpics.constans.UiState
import com.example.wallpics.data.retrofitService
import kotlinx.coroutines.launch

class WallpaperViewModel : ViewModel() {

    var imageList = mutableStateOf<List<WallpaperModel>>(emptyList()) // La lista de wallpapers
        private set
    var selectedWallpaper: WallpaperModel? = null
        private set
    var currentPage: Int = 1
    var lastPage: Int = 1
    var uiState: UiState by mutableStateOf(UiState.Loading)
        private set

    // Función para obtener los wallpapers desde la API
    fun getWallpapers(purity: Int, page: Int) {
        viewModelScope.launch {
            uiState = try {
                UiState.Loading
                // Primer paso: obtener las imágenes
                val response = retrofitService.webService.getWallpapers(purity, page)
                if (response.isSuccessful) {
                    val wallpapersResponse = response.body()
                    println("Search response: $wallpapersResponse")
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

    fun searchByQuery(query: String, purity: Int, page: Int) {
        viewModelScope.launch {
            uiState = try {
                UiState.Loading
                val response = retrofitService.webService.searchByQuery(query, purity, page)
                if (response.isSuccessful) {
                    // Deserializamos el cuerpo de la respuesta como WallpaperResponse
                    val wallpapersResponse = response.body() // Esto es de tipo WallpaperResponse
                    println("Respuesta de la API: $wallpapersResponse")
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

    fun selectWallpaper(wallpaper: WallpaperModel) {
        selectedWallpaper = null
        viewModelScope.launch {
            uiState = try {
                UiState.Loading
                val detailsResponse = retrofitService.webService.getWallpaperById(wallpaper.id)
                if (detailsResponse.isSuccessful) {
                    val wallpaperDetails = detailsResponse.body()
                    wallpaperDetails?.let {
                        val wallpaperWithDetails = it.data
                        // Ahora fuera del let, podemos crear wallpaperWithCompleteDetails
                        val wallpaperWithCompleteDetails = wallpaper.copy(
                            uploader = wallpaperWithDetails.uploader
                        )
                        selectedWallpaper = wallpaperWithCompleteDetails
                    }
                    UiState.Success
                } else {
                    println("Error al obtener detalles de la imagen: ${detailsResponse.message()}")
                    UiState.Error
                }

            } catch (e: Exception) {
                println("Error al obtener detalles de la imagen: ${e.message}")
                UiState.Error
            }
        }
    }

}