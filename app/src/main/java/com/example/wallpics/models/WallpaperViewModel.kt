package com.example.wallpics.models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpics.data.retrofitService
import kotlinx.coroutines.launch

class WallpaperViewModel: ViewModel() {

    var imageList = mutableStateOf<List<WallpaperModel>>(emptyList()) // La lista de wallpapers
        private set
    var selectedWallpaper: WallpaperModel? = null
        private set
    var currentPage: Int=  1
    var lastPage: Int= 1

    // Función para obtener los wallpapers desde la API
    fun getWallpapers(purity: Int, page: Int) {
        viewModelScope.launch {
            try {
                // Primer paso: obtener las imágenes
                val response = retrofitService.webService.getWallpapers(purity, page)
                if (response.isSuccessful) {
                    val wallpapersResponse = response.body()
                    println("Search response: $wallpapersResponse")
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

    fun searchByQuery(query: String, purity: Int, page: Int) {
        viewModelScope.launch {
            try {
                val response = retrofitService.webService.searchByQuery(query, purity, page)
                if (response.isSuccessful) {
                    // Deserializamos el cuerpo de la respuesta como WallpaperResponse
                    val wallpapersResponse = response.body() // Esto es de tipo WallpaperResponse
                    println("Respuesta de la API: $wallpapersResponse")
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

    fun selectWallpaper (wallpaper: WallpaperModel) {
        selectedWallpaper = null
        viewModelScope.launch {
            try {
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
                } else {
                    println("Error al obtener detalles de la imagen: ${detailsResponse.message()}")
                }

            } catch (e: Exception) {
                println("Error al obtener detalles de la imagen: ${e.message}")
            }
        }
    }

}