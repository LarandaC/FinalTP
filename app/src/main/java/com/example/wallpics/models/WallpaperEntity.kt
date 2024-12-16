package com.example.wallpics.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class WallpaperEntity(
    @PrimaryKey val id: String,
    val shortUrl: String,
    val category: String,
    val resolution: String,
    val ratio: String,
    val path: String,
    val thumbs: String,
    val tags: String,
    val uploader: String
)