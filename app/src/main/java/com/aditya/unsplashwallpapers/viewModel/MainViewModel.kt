package com.aditya.unsplashwallpapers.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.aditya.unsplashwallpapers.repository.ApplicationRepo

class MainViewModel @ViewModelInject constructor(private val repo: ApplicationRepo, @Assisted state: SavedStateHandle): ViewModel() {


}