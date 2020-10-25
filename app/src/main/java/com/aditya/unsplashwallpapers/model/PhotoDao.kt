package com.aditya.unsplashwallpapers.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PhotoDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPic(photoEntity: PhotoEntity)

    @Delete
    suspend fun deletePic(photoEntity: PhotoEntity)

    @Query("SELECT * FROM photo_tb WHERE id =(:idSent)")
    fun getPic(idSent:String):LiveData<PhotoEntity?>

    @Query("SELECT * FROM photo_tb")
    fun getAllPics(): LiveData<List<PhotoEntity>>
}