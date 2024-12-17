package com.example.wallpics.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    // Obtener la instancia de la BD
    fun getDatabase(context: Context): AppDatabase {
        // Devuelve la instancia si ya existe
        return INSTANCE ?: synchronized(this) {
            // Si no existe, crea la BD
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "wallpaper_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
