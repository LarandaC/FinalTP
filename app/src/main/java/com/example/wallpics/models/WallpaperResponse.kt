package com.example.wallpics.models

import com.google.gson.annotations.SerializedName

data class WallpaperResponse (
    @SerializedName("data")
    var resultados: List<WallpaperModel>,
    var meta: Meta,
)

data class WallpaperDetailsResponse(
    val data: WallpaperModel
)