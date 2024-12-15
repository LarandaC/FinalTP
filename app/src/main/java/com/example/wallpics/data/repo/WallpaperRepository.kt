package com.example.wallpics.data.repo

import com.example.wallpics.data.WebService


class WallpaperRepository(private val webService: WebService) {

    suspend fun getPopularWallpapers(purity: Int, page: Int) =
        webService.getPopularWallpapers(purity = purity, page = page)
}

