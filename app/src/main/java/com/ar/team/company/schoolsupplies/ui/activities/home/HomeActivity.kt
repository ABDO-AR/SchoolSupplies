package com.ar.team.company.schoolsupplies.ui.activities.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.databinding.ActivityHomeBinding
import com.ar.team.company.schoolsupplies.ui.activities.add.AddToolActivity
import dagger.hilt.android.AndroidEntryPoint

@Suppress("unused")
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    // Fields:
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val model: HomeViewModel by viewModels()

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
        binding.homeHostContainer.post {
            // Posting:
            NavigationUI.setupWithNavController(binding.navigation, controller)
            // Developing:
            binding.addingButton.setOnClickListener {
                // Initializing:
                val addToolIntent = Intent(this, AddToolActivity::class.java)
                // Animations:
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                // Starting:
                startActivity(addToolIntent)
            }
        }
    }
}