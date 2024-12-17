package com.example.wallpics.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "downloads")
data class DownloadEntity(
    @PrimaryKey val id: String,
    val shortUrl: String,
    val category: String,
    val resolution: String,
    val ratio: String,
    val path: String,
    val fileSize: String,
    val thumbs: String,
    val uploader: String
) {
}