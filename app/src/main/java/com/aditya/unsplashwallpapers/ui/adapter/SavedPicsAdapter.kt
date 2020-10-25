package com.aditya.unsplashwallpapers.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.unsplashwallpapers.R
import com.aditya.unsplashwallpapers.databinding.ItemUnsplashPicBinding
import com.aditya.unsplashwallpapers.model.PhotoEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SavedPicsAdapter(): RecyclerView.Adapter<SavedPicsAdapter.SavedHolder>() {

    private var onItemClickListener: ((PhotoEntity)->Unit)? =null

    fun setOnItemClickListener(listener: (PhotoEntity)-> Unit){ onItemClickListener = listener }

    inner class SavedHolder(private val binding: ItemUnsplashPicBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(picObj: PhotoEntity){
            binding.apply {
                root.setOnClickListener { onItemClickListener?.let { it(picObj) } }

                Glide.with(itemView)
                    .load(picObj.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)
                tvUsername.text = picObj.user.username
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<PhotoEntity>(){
        override fun areItemsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean { return oldItem.id == newItem.id }
        override fun areContentsTheSame(oldItem: PhotoEntity, newItem: PhotoEntity): Boolean { return oldItem == newItem }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedHolder {
        val binding = ItemUnsplashPicBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SavedHolder, position: Int) {
        val currentItem = differ.currentList[position]
        currentItem.let { holder.bind(it) }
    }
}