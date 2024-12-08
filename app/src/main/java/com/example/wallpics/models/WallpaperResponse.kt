package com.example.wallpics.models

import com.google.gson.annotations.SerializedName

data class WallpaperResponse (
    @SerializedName("data")
    var resultados: List<WallpaperModel>
)