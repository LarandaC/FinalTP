package com.example.wallpics.models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpics.data.retrofitService
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel()  {

    var imageList = mutableStateOf<List<WallpaperModel>>(emptyList()) // La lista de wallpapers
        private set
    var currentPage: Int =  1
    var lastPage: Int = 1


    fun searchByQuery(query: String, purity: Int, page: Int) {
        viewModelScope.launch {
            try {
                val response = retrofitService.webService.searchByQuery(query, purity, page)
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
}