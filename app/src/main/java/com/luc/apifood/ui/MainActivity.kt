package com.luc.apifood.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.luc.apifood.R
import com.luc.apifood.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //setTheme(R.style.Theme_ApiFood)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{controller, destination, arguments ->
            if (destination.label != "fragment_home") {
                binding.bottomNavigationView.animate().translationY(binding.bottomNavigationView.height.toFloat()).duration = 550
            } else {
                binding.bottomNavigationView.animate().translationY(0f)

            }
        }

    }
}