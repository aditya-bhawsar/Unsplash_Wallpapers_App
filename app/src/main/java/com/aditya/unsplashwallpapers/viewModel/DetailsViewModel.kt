package com.aditya.unsplashwallpapers.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.unsplashwallpapers.model.PhotoEntity
import com.aditya.unsplashwallpapers.repository.ApplicationRepo
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(private val applicationRepo: ApplicationRepo): ViewModel() {

    fun insertPic(photoEntity: PhotoEntity)= viewModelScope.launch { applicationRepo.addPic(photoEntity) }
    fun deletePic(photoEntity: PhotoEntity)= viewModelScope.launch { applicationRepo.deletePic(photoEntity) }

    fun checkForString(id: String) = applicationRepo.getPic(id)

}