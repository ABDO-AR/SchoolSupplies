package com.ar.team.company.schoolsupplies.ui.activitys.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import com.ar.team.company.schoolsupplies.databinding.ActivityMainBinding
import com.ar.team.company.schoolsupplies.ui.activitys.home.HomeActivity
import com.ar.team.company.schoolsupplies.ui.activitys.sign.SignActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    // Fields:
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val splashHandler: Handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        supportActionBar?.hide().also { setContentView(binding.root) } // Setting content to the root of binding.
        // Setting(LightMode):
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // init Firebase Auth
        auth = FirebaseAuth.getInstance()
        // Initializing:
        splashHandler.postDelayed(::onSplash, 1000)
    }

    // Method(OnSplash):
    private fun onSplash() { // This method will call after 1 second.
        val splashIntent: Intent
        // Initializing:
        splashIntent = if (auth?.currentUser !==null)
            Intent(this, HomeActivity::class.java)
        else
            Intent(this, SignActivity::class.java)
        // Overriding:
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        // Starting:
        startActivity(splashIntent).also { finish() }
    }
}