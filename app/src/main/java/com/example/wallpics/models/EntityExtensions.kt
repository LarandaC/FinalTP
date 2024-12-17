package com.example.wallpics.models

import com.google.gson.Gson

fun WallpaperEntity.toModel(): WallpaperModel {
    val gson = Gson()

    fun <T> convertJson(json: String?, clazz: Class<T>, default: T): T {
        return try {
            json?.let { gson.fromJson(it, clazz) } ?: default
        } catch (e: Exception) {
            default
        }
    }

    return WallpaperModel(
        id = this.id,
        url = "",
        shortUrl = this.shortUrl,
        category = this.category,
        fileSize = "",
        purity = "",
        createdAt = "",
        resolution = this.resolution,
        favorites = "",
        thumbs = convertJson(this.thumbs, Thumbs::class.java, Thumbs("", "", "")),
        ratio = this.ratio,
        dimensionX = 0,
        dimensionY = 0,
        path = this.path,
        tags = convertJson(this.tags, Array<Tag>::class.java, emptyArray<Tag>()).toList(),
        uploader = convertJson(this.uploader, Uploader::class.java, Uploader("", "", Avatar("", "", "", "")))
    )
}

fun DownloadEntity.toModel(): WallpaperModel {
    val gson = Gson()

    fun <T> convertJson(json: String?, clazz: Class<T>, default: T): T {
        return try {
            json?.let { gson.fromJson(it, clazz) } ?: default
        } catch (e: Exception) {
            default
        }
    }

    return WallpaperModel(
        id = this.id,
        url = "",
        shortUrl = this.shortUrl,
        category = this.category,
        fileSize = this.fileSize,
        purity = "",
        createdAt = "",
        resolution = this.resolution,
        favorites = "",
        thumbs = gson.fromJson(this.thumbs, Thumbs::class.java),
        ratio = this.ratio,
        dimensionX = 0,
        dimensionY = 0,
        path = this.path,
        tags = convertJson("", Array<Tag>::class.java, emptyArray<Tag>()).toList(),
        uploader = convertJson("", Uploader::class.java, Uploader("", "", Avatar("", "", "", "")))
    )
}