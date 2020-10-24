package com.aditya.unsplashwallpapers.util

import androidx.paging.PagingSource
import com.aditya.unsplashwallpapers.api.UnsplashApi
import com.aditya.unsplashwallpapers.model.PhotoEntity
import retrofit2.HttpException
import java.io.IOException

class UnsplashPagingSource(private val unsplashApi: UnsplashApi, private val query:String): PagingSource<Int, PhotoEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoEntity> {

        val pos = params.key ?:1

        return try{
            val response = unsplashApi.searchPics(query, pos, params.loadSize)
            val photo = response.results

            LoadResult.Page(
                data = photo,
                prevKey = if(pos==1) null else pos-1,
                nextKey = if(photo.isEmpty()) null else pos+1
            )
        }
        catch (e: IOException){
            e.printStackTrace()
            LoadResult.Error(e)
        }
        catch (e: HttpException){
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}