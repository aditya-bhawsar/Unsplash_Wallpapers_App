package com.aditya.unsplashwallpapers.ui.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.aditya.unsplashwallpapers.R
import com.aditya.unsplashwallpapers.databinding.DetailsFragmentBinding
import com.aditya.unsplashwallpapers.model.PhotoEntity
import com.aditya.unsplashwallpapers.viewModel.DetailsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.details_fragment) {

    private val args by navArgs<DetailsFragmentArgs>()
    private val viewModel by viewModels<DetailsViewModel>()

    lateinit var photo: PhotoEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val binding = DetailsFragmentBinding.bind(view)
        binding.apply {
            photo = args.Photo
            Glide.with(this@DetailsFragment)
                .load(photo.urls.regular)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        progressBar.isVisible = false
                        Snackbar.make(binding.root,"Something went wrong!!!",Snackbar.LENGTH_INDEFINITE).show()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = (photo.description != null)
                        return false
                    }
                })
                .into(imageView)

            textViewDescription.text = photo.description

            val url = Uri.parse(photo.user.attributionUrl)
            val i = Intent(Intent.ACTION_VIEW, url)

            textViewCreator.apply {
                text = "Photo by ${photo.user.name} on Unsplash"
                setOnClickListener { requireContext().startActivity(i) }
                paint.isUnderlineText = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_menu, menu)

        val saveItem = menu.findItem(R.id.save_menu)
        val deleteItem = menu.findItem(R.id.delete_menu)

        viewModel.checkForString(photo.id).observe(viewLifecycleOwner, Observer {
            saveItem.isVisible = (it==null)
            deleteItem.isVisible  = (it!=null)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_menu->{ viewModel.insertPic(photo) }
            R.id.delete_menu->{ viewModel.deletePic(photo) }
        }
        return super.onOptionsItemSelected(item)
    }
}