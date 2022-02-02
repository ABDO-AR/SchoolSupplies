package com.ar.team.company.schoolsupplies.ui.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.databinding.FragmentOrdersBinding
import com.ar.team.company.schoolsupplies.ui.activitys.home.HomeViewModel

class OrdersFragment : Fragment() {

    // Fields:
    private var _binding: FragmentOrdersBinding? = null
    private val binding: FragmentOrdersBinding get() = _binding!!

    // ViewModel:
    private val model: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "OrdersFragment"
    }

    // Method(OnCreateView):
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Initializing:
        _binding = FragmentOrdersBinding.inflate(layoutInflater, container, false)
        // Returning:
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        // Initializing:
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
}