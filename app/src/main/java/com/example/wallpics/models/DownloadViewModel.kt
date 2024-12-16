package com.example.wallpics.models

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class DownloadViewModel: ViewModel()  {
    private val _downloads = mutableStateListOf<DetailImageItemModel>()
    val downloads: List<DetailImageItemModel> get() = _downloads

    fun addDownload(item: DetailImageItemModel) {
        _downloads.add(item)
    }

    fun removeDownload(item: DetailImageItemModel) {
        _downloads.remove(item)
    }
}
