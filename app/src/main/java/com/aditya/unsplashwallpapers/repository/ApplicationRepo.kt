package com.aditya.unsplashwallpapers.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.aditya.unsplashwallpapers.api.UnsplashApi
import com.aditya.unsplashwallpapers.model.PhotoDao
import com.aditya.unsplashwallpapers.model.PhotoEntity
import com.aditya.unsplashwallpapers.util.UnsplashPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepo @Inject constructor(private val api: UnsplashApi, private val photoDao: PhotoDao) {

    fun getSearchResults(query: String)  = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { UnsplashPagingSource(api,query) }
    ).liveData

    suspend fun addPic(photoEntity: PhotoEntity) = photoDao.insertPic(photoEntity)
    suspend fun deletePic(photoEntity: PhotoEntity) = photoDao.deletePic(photoEntity)

}