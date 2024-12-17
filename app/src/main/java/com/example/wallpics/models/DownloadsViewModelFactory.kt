package com.example.wallpics.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wallpics.data.DownloadDao

class DownloadsViewModelFactory (private val downloadDao: DownloadDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DownloadViewModel::class.java)) {
            return DownloadViewModel(downloadDao) as T
        }
        throw IllegalArgumentException("ViewModel class desconocida")
    }
}