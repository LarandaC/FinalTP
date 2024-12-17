package com.example.wallpics.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wallpics.models.DownloadEntity

@Dao
interface DownloadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDownload(download: DownloadEntity)

    @Delete
    suspend fun removeDownload(download: DownloadEntity)

    @Query("SELECT * FROM downloads")
    fun getAllDownloads(): LiveData<List<DownloadEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM downloads WHERE id = :id)")
    fun isDownloaded(id: String): LiveData<Boolean>
}