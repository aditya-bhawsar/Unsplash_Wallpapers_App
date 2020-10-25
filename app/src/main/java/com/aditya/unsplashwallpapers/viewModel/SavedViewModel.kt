package com.aditya.unsplashwallpapers.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.aditya.unsplashwallpapers.repository.ApplicationRepo

class SavedViewModel @ViewModelInject constructor(private val applicationRepo: ApplicationRepo) : ViewModel(){

    fun getAllPics() = applicationRepo.getAllPics()

}