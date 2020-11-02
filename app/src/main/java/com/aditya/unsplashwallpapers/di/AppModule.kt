package com.aditya.unsplashwallpapers.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aditya.unsplashwallpapers.api.UnsplashApi
import com.aditya.unsplashwallpapers.model.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesUnSplashApi(retrofit: Retrofit) : UnsplashApi = retrofit.create(UnsplashApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)
            = Room.databaseBuilder(context, PhotoDatabase::class.java, "db_photo").allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun providePhotoDao(db: PhotoDatabase) = db.getPhotoDao()
}
