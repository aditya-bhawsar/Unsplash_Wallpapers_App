package com.aditya.unsplashwallpapers.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.aditya.unsplashwallpapers.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(750)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        findNavController(R.id.nav_host_fragment_main)

        val appBarConfiguration = AppBarConfiguration(findNavController(R.id.nav_host_fragment_main).graph)
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment_main), appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_main).navigateUp() || super.onSupportNavigateUp()
    }

}