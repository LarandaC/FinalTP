package com.example.wallpics.data

import com.example.wallpics.models.WallpaperDetailsResponse
import com.example.wallpics.models.WallpaperResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {
    @GET("search")
    suspend fun getWallpapers(
        @Query("purity") purity: Int,
        @Query("page") page: Int,
    ): Response<WallpaperResponse>

    @GET("search")
    suspend fun searchByQuery(
        @Query("q") q: String,
        @Query("purity") purity: Int,
        @Query("page") page: Int,
    ): Response<WallpaperResponse>

    @GET("search")
    suspend fun getPopularWallpapers(
        @Query("purity") purity: Int,
        @Query("page") page: Int,
        @Query("sorting") sorting: String = "toplist",
        @Query("topRange") topRange: String = "1W",
    ): Response<WallpaperResponse>

    @GET("w/{id}")
    suspend fun getWallpaperById(
        @Path("id") id: String
    ): Response<WallpaperDetailsResponse>
}
