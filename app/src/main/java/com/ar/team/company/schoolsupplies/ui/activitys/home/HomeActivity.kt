package com.ar.team.company.schoolsupplies.ui.activitys.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ar.team.company.schoolsupplies.databinding.ActivityHomeBinding
import com.ar.team.company.schoolsupplies.ui.adapters.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {

    // Fields:
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val model: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    //TabLayoutMediator
    var mediator: TabLayoutMediator? = null

    //Adapter
    var adapter: PagerAdapter? = null
    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "HomeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }

        //Initializing

        //Initializing
        adapter = PagerAdapter(supportFragmentManager, lifecycle)
        mediator = TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.setText(
                adapter!!.getHeader(
                    position
                )
            )
        }

        //setAdapter

        //setAdapter
        binding.viewPager.adapter = adapter

        //AttachMediator

        //AttachMediator
        if (!mediator!!.isAttached) mediator!!.attach()

        // Initializing:
    }
}