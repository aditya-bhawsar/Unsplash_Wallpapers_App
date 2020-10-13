package com.aditya.unsplashwallpapers.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aditya.unsplashwallpapers.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepo @Inject constructor(private val api: UnsplashApi) {

    fun getSearchResults(query: String)  = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { }
    )

}