@file:Suppress("unused")

package com.ar.team.company.schoolsupplies.ui.fragments.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ar.team.company.schoolsupplies.control.adapter.OrdersAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.FragmentOrdersBinding
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.ui.activities.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class OrdersFragment : Fragment() {

    // Binding::
    private var _binding: FragmentOrdersBinding? = null
    private val binding: FragmentOrdersBinding get() = _binding!!

    // Orders:
    private var orders: ArrayList<Tool> = ArrayList()
    private lateinit var adapter: OrdersAdapter

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
        adapter = OrdersAdapter(requireContext(), orders).also { progressToggle(false) }
        // Setting:
        binding.rvListOrder.adapter = adapter
        binding.backButton.setOnClickListener { requireActivity().onBackPressed() }
        // Getting:
        getDataOrders()
    }

    // Method(ProgressToggle):
    @Suppress("SameParameterValue")
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.rvListOrder.visibility = if (visible) View.GONE else View.VISIBLE
        binding.generalToolsProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getDataOrders() {
        // Getting:
        DatabaseManager.ordersDBReference.child(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener { it ->
            // Looping:
            for (tool in it.children) {
                // Adding:
                tool.getValue(Tool::class.java)?.let { orders.add(it) }
            }
            // Debugging:
            Log.e("size ", orders.size.toString())


            // Notifying:
            adapter.notifyDataSetChanged()
        }
    }
}