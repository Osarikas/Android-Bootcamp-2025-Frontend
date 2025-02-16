package ru.sicampus.bootcamp2025.ui

import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import ru.sicampus.bootcamp2025.R
import ru.sicampus.bootcamp2025.databinding.ActivityMainBinding
import ru.sicampus.bootcamp2025.util.setActiveMenuItem

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val windowInsetsController = window.insetsController
        windowInsetsController?.hide(android.view.WindowInsets.Type.statusBars() or android.view.WindowInsets.Type.navigationBars())

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val menuButtons = setOf(binding.menuVolunteers, binding.menuHome, binding.menuProfile)

        binding.menuVolunteers.setOnClickListener {
            navController.navigate(R.id.freeVolunteersListFragment)
            setActiveMenuItem(binding.menuVolunteers, menuButtons,this)
        }

        binding.menuHome.setOnClickListener {
            navController.navigate(R.id.mainPageFragment)
            setActiveMenuItem(binding.menuHome, menuButtons, this)
        }

        binding.menuProfile.setOnClickListener {
            navController.navigate(R.id.profileFragment)
            setActiveMenuItem(binding.menuProfile, menuButtons, this)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.registerFirstFragment, R.id.registerSecondFragment, R.id.loginFragment, R.id.starterFragment -> {
                    toggleMenuVisibility(false)
                }
                else -> {
                    toggleMenuVisibility(true)
                }
            }
        }

    }
    private fun toggleMenuVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.menu.visibility = View.VISIBLE
        } else {
            binding.menu.visibility = View.GONE
        }
    }

}