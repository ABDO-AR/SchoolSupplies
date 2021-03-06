package com.ar.team.company.schoolsupplies.ui.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.ar.team.company.schoolsupplies.databinding.ActivityMainBinding
import com.ar.team.company.schoolsupplies.ui.activities.home.HomeActivity
import com.ar.team.company.schoolsupplies.ui.activities.sign.SignActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // Fields:
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val splashHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

    // Fields:
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        supportActionBar?.hide().also { setContentView(binding.root) } // Setting content to the root of binding.
        // Setting(LightMode):
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // Initializing:
        splashHandler.postDelayed(::onSplash, 1000)
    }

    // Method(OnSplash):
    private fun onSplash() { // This method will call after 1 second.
        // Initializing:
        val splashIntent = Intent(this, if (auth.currentUser !== null) HomeActivity::class.java else SignActivity::class.java)
        // Overriding:
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        // Starting:
        startActivity(splashIntent).also { finish() }
    }
}