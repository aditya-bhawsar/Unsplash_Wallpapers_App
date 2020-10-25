package com.aditya.unsplashwallpapers.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.aditya.unsplashwallpapers.R
import com.aditya.unsplashwallpapers.databinding.SavedFragmentBinding
import com.aditya.unsplashwallpapers.ui.adapter.SavedPicsAdapter
import com.aditya.unsplashwallpapers.viewModel.SavedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment: Fragment(R.layout.saved_fragment) {

    private val viewModel by viewModels<SavedViewModel>()

    private var _binding: SavedFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = SavedFragmentBinding.bind(view)

        val adapter = SavedPicsAdapter()

        binding.apply {
            adapter.setOnItemClickListener{
                val action= SavedFragmentDirections.actionSavedFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            }
            listRv.adapter = adapter
        }

        viewModel.getAllPics().observe(viewLifecycleOwner, Observer {
            binding.apply {
                listRv.isVisible = it.isNotEmpty()
                errorTv.isVisible = it.isEmpty()
            }
            adapter.differ.submitList(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}