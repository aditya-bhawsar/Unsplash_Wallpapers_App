package com.aditya.unsplashwallpapers.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.aditya.unsplashwallpapers.R
import com.aditya.unsplashwallpapers.databinding.MainFragmentBinding
import com.aditya.unsplashwallpapers.model.PhotoEntity
import com.aditya.unsplashwallpapers.ui.activity.MainActivity
import com.aditya.unsplashwallpapers.ui.adapter.PhotoAdapter
import com.aditya.unsplashwallpapers.ui.adapter.PhotoLoadStateAdapter
import com.aditya.unsplashwallpapers.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :Fragment(R.layout.main_fragment), PhotoAdapter.OnItemClick{

    private val viewModel by viewModels<MainViewModel>()

    private var _binding : MainFragmentBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        _binding = MainFragmentBinding.bind(view)
        val adapter = PhotoAdapter(this)

        binding.apply {
            listRv.setHasFixedSize(true)
            listRv.itemAnimator = null
            listRv.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PhotoLoadStateAdapter{adapter.retry()},
                footer = PhotoLoadStateAdapter{adapter.retry()}
            )
            btnRetry.setOnClickListener{ adapter.retry() }
        }

        viewModel.photo.observe(viewLifecycleOwner, Observer{ adapter.submitData(viewLifecycleOwner.lifecycle,it) })

        adapter.addLoadStateListener {combinedLoadState->
            binding.apply {
                progressBar.isVisible = combinedLoadState.source.refresh is LoadState.Loading
                listRv.isVisible = combinedLoadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = combinedLoadState.source.refresh is LoadState.Error
                textViewMessage.isVisible = combinedLoadState.source.refresh is LoadState.Error

                if(combinedLoadState.source.refresh is LoadState.NotLoading
                    && combinedLoadState.append.endOfPaginationReached
                    && adapter.itemCount < 1){
                    listRv.isVisible = false
                    textViewEmpty.isVisible = true
                }
                else{ textViewEmpty.isVisible = false }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val savedItem = menu.findItem(R.id.saved_menu)
        val searchView = searchItem.actionView as SearchView

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                savedItem.isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                savedItem.isVisible = true
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){

                    binding.listRv.scrollToPosition(0)
                    viewModel.searchPics(query)
                    searchView.clearFocus()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean { return true }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.saved_menu){ findNavController().navigate(R.id.action_mainFragment_to_savedFragment) }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: PhotoEntity) {
        val action= MainFragmentDirections.actionMainFragmentToDetailsFragment(photo)
        findNavController().navigate(action)
    }
}