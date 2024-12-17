package com.example.wallpics.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpics.data.DownloadDao
import kotlinx.coroutines.launch

class DownloadViewModel(private val downloadDao: DownloadDao): ViewModel()  {

    val downloads: LiveData<List<DownloadEntity>> = downloadDao.getAllDownloads()

    fun addDownload(download: DownloadEntity) {
        viewModelScope.launch {
            downloadDao.addDownload(download)
        }
    }

    fun removeDownload(download: DownloadEntity) {
        viewModelScope.launch {
            downloadDao.removeDownload(download)
        }
    }
}
