package com.ar.team.company.schoolsupplies.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ar.team.company.schoolsupplies.control.adapter.PagerAdapter
import com.ar.team.company.schoolsupplies.databinding.FragmentHomeBinding
import com.ar.team.company.schoolsupplies.ui.activitys.home.HomeViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    // Fields:
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    // ViewModel:
    private val model: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    // TabLayoutMediator
    private var mediator: TabLayoutMediator? = null

    // Adapter
    private var adapter: PagerAdapter? = null

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "HomeFragment"
    }

    // Method(OnCreateView):
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Initializing:
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        // Returning:
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        //Initializing
        adapter = PagerAdapter(requireActivity().supportFragmentManager, lifecycle).also { it.initData(requireContext()) }
        mediator = TabLayoutMediator(binding.tabLayout, binding.viewPager, ::onConfigureTabs)
        // Developing:
        binding.viewPager.adapter = adapter
        // Attaching:
        if (!mediator!!.isAttached) mediator!!.attach()
    }

    // Method(OnConfigureTabs):
    private fun onConfigureTabs(tab: TabLayout.Tab, index: Int) = tab.setText(adapter!!.getHeader(index))

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
}