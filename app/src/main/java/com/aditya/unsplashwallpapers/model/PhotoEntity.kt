package com.aditya.unsplashwallpapers.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "photo_tb")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false)
    var id :String,
    var description: String?,
    @Embedded
    var urls: PhotoUrl,
    @Embedded
    var user: PhotoUser
):Parcelable {}