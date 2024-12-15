package com.example.wallpics.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpics.data.repo.WallpaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularWallpapersViewModel(private val repository: WallpaperRepository) : ViewModel() {


    private val _popularWallpapers = MutableStateFlow<List<WallpaperModel>>(emptyList())
    val popularWallpapers: StateFlow<List<WallpaperModel>> = _popularWallpapers


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadPopularWallpapers(purity: Int = 100, page: Int = 1) {
        viewModelScope.launch {
            try {

                val response = repository.getPopularWallpapers(purity, page)

                if (response.isSuccessful) {

                    _popularWallpapers.value = response.body()?.resultados ?: emptyList()
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {

                _errorMessage.value = "Error: ${e.localizedMessage}"
            }
        }
    }
}