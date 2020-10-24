package com.aditya.unsplashwallpapers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.unsplashwallpapers.R
import com.aditya.unsplashwallpapers.databinding.ItemUnsplashPicBinding
import com.aditya.unsplashwallpapers.model.PhotoEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class PhotoAdapter(private val listener: OnItemClick): PagingDataAdapter<PhotoEntity, PhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    interface OnItemClick{ fun onItemClick(photo: PhotoEntity) }

    inner class PhotoViewHolder(private val binding: ItemUnsplashPicBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    item?.let{ listener.onItemClick(item) }
                }
            }
        }
        fun bind(photo: PhotoEntity){
            binding.apply {
                Glide.with(itemView)
                    .load(photo.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                tvUsername.text = photo.user.username
            }
        }
    }

    companion object{
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<PhotoEntity>(){
            override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity) = oldItem.id==newItem.id
            override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity) = oldItem==newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemUnsplashPicBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }


}