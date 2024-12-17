package com.example.wallpics.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpics.data.FavoritesDao
import kotlinx.coroutines.launch

class FavoritesViewModel(private val favoritesDao: FavoritesDao) : ViewModel() {

    // Obtiene todos los favoritos como Live Data
    val favorites: LiveData<List<WallpaperEntity>> = favoritesDao.getAllFavorites()

    fun isFavorite(id: String): LiveData<Boolean> {
        return favoritesDao.isFavorite(id)
    }

    fun addFavorite(wallpaper: WallpaperEntity) {
        viewModelScope.launch {
            favoritesDao.addFavorite(wallpaper)
        }
    }

    fun removeFavorite(wallpaper: WallpaperEntity) {
        viewModelScope.launch {
            favoritesDao.removeFavorite(wallpaper)
        }
    }
}