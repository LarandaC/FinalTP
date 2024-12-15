package com.example.wallpics.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wallpics.models.WallpaperEntity

// Define la base de datos de Room para la aplicación
@Database(entities = [WallpaperEntity::class, User::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
    abstract fun userDao(): UserDao
}