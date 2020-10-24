package com.aditya.unsplashwallpapers.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.aditya.unsplashwallpapers.repository.ApplicationRepo

class MainViewModel @ViewModelInject constructor(private val repo: ApplicationRepo, @Assisted state: SavedStateHandle): ViewModel() {

    private val currentQuery = state.getLiveData("query", "wallpaper")
    val photo= currentQuery.switchMap { repo.getSearchResults(it).cachedIn(viewModelScope) }
    fun searchPics(query:String){ currentQuery.postValue(query) }
}