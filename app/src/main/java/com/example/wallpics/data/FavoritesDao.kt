package com.example.wallpics.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpics.models.WallpaperEntity

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(wallpaper: WallpaperEntity)

    @Delete
    suspend fun removeFavorite(wallpaper: WallpaperEntity)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData<List<WallpaperEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    fun isFavorite(id: String): LiveData<Boolean>
}