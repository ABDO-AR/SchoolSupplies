package com.ar.team.company.schoolsupplies.ui.activitys.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ar.team.company.schoolsupplies.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    // Fields:
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val model: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:
    }
}