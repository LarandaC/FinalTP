package com.example.wallpics.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class WallpaperModel(
    @SerializedName("id")
    var id: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("short_url")
    var shortUrl: String,
    @SerializedName("category")
    var category: String,
    @SerializedName("file_size")
    var fileSize: String,
    @SerializedName("purity")
    var purity: String,
    @SerializedName("created_at")
    var createdAt: String,
    var resolution: String,
    var favorites: String,
    @SerializedName("thumbs")
    var thumbs: Thumbs,
    var ratio: String,
    @SerializedName("dimension_x")
    var dimensionX: Number,
    @SerializedName("dimension_y")
    var dimensionY: Number,
    var path: String,
    
    var uploader : Uploader
)

data class Uploader(
    val username: String,
    val group: String,
    val avatar: Avatar
)

data class Avatar(
    @SerializedName("200px")
    val large: String,
    @SerializedName("128px")
    val medium: String,
    @SerializedName("32px")
    val small: String,
    @SerializedName("20px")
    val tiny: String
)

data class Thumbs(
    @SerializedName("large")
    var large: String, // Miniatura grande
    @SerializedName("original")
    var original: String, // Imagen original
    @SerializedName("small")
    var small: String // Miniatura pequeña
)

data class Meta (
    @SerializedName("current_page")
    var currentPage: Int,
    @SerializedName("last_page")
    var lastPage: Int,
    @SerializedName("per_page")
    var perPage: Int,
    var total: Int,
)

// Función de conversión de modelo a entidad
fun WallpaperModel.toEntity(): WallpaperEntity {
    val gson = Gson()
    return WallpaperEntity(
        id = this.id,
        shortUrl = this.shortUrl,
        category = this.category,
        resolution = this.resolution,
        ratio = this.ratio,
        path = this.path,
        thumbs = gson.toJson(this.thumbs),
        uploader = gson.toJson(this.uploader)
    )
}

// Función de conversión de modelo a entidad
fun WallpaperModel.toDownloadEntity(): DownloadEntity {
    val gson = Gson()
    return DownloadEntity(
        id = this.id,
        shortUrl = this.shortUrl,
        category = this.category,
        resolution = this.resolution,
        ratio = this.ratio,
        path = this.path,
        thumbs = gson.toJson(this.thumbs),
        fileSize = this.fileSize,
        uploader =  gson.toJson(this.uploader)
    )
}