package com.aditya.unsplashwallpapers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aditya.unsplashwallpapers.databinding.UnsplashLoadFooterBinding

class PhotoLoadStateAdapter(private val retry: () -> Unit): LoadStateAdapter<PhotoLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: UnsplashLoadFooterBinding): RecyclerView.ViewHolder(binding.root){
        init { binding.btnRetry.setOnClickListener { retry.invoke() }}

        fun bind(loadState: LoadState){
            binding.apply {
                pbLoader.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState !is LoadState.Loading
                textError.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = UnsplashLoadFooterBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) { holder.bind(loadState) }
}