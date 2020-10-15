package com.aditya.unsplashwallpapers.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotoUser(
    val name: String,
    val username: String
):Parcelable {
    val attributionUrl get() = "https://unsplash.com/$username?utm_source=Image Search App&utm_medium=referral"
}