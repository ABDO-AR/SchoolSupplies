package com.ar.team.company.schoolsupplies.ui.activities.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ar.team.company.schoolsupplies.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignActivity : AppCompatActivity() {

    // Fields:
    private val binding: ActivitySignBinding by lazy { ActivitySignBinding.inflate(layoutInflater) }
    private val model: SignViewModel by viewModels()

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