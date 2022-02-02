package com.ar.team.company.schoolsupplies.ui.activitys.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.databinding.ActivityHomeBinding
import com.ar.team.company.schoolsupplies.control.adapter.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    // Fields:
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val model: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    // NavController:
    private val controller: NavController by lazy { Navigation.findNavController(this, R.id.home_host_container) }

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "HomeActivity"
    }

    // Method(OnCreate):
    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:
        binding.homeHostContainer.post { NavigationUI.setupWithNavController(binding.navigation, controller) }
    }
}