package com.example.wallpics.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallpics.data.FavoritesDao

// Factory para crear instancias de FavoritesViewModel
class FavoritesViewModelFactory(private val favoritesDao: FavoritesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(favoritesDao) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}