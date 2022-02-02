package com.ar.team.company.schoolsupplies.ui.activitys.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ar.team.company.schoolsupplies.databinding.ActivitySignBinding

class SignActivity : AppCompatActivity() {

    // Fields:
    private val binding: ActivitySignBinding by lazy { ActivitySignBinding.inflate(layoutInflater) }
    private val model: SignViewModel by lazy { ViewModelProvider(this)[SignViewModel::class.java] }

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "SignActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:
    }
}