package com.ar.team.company.schoolsupplies.ui.fragments.tools.special

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.adapter.SpecialToolsAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.FragmentSpecialToolsBinding
import com.ar.team.company.schoolsupplies.model.intentions.HomeIntentions
import com.ar.team.company.schoolsupplies.model.states.HomeViewStates
import com.ar.team.company.schoolsupplies.ui.activities.home.HomeViewModel
import com.ar.team.company.schoolsupplies.ui.fragments.tools.general.GeneralToolsFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpecialToolsFragment : Fragment() {

    // Fields:
    private var _binding: FragmentSpecialToolsBinding? = null
    private val binding: FragmentSpecialToolsBinding get() = _binding!!


    // ViewModel:
    private val model: HomeViewModel by viewModels()

    // SelectedType:
    private lateinit var selectedType: String

    // Adapter:
    private lateinit var adapter: SpecialToolsAdapter

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "SpecialToolsFragment"
    }

    // Method(OnCreateView):
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Initializing:
        _binding = FragmentSpecialToolsBinding.inflate(layoutInflater, container, false)
        // Returning:
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        // Initializing:
        selectedType = getString(R.string.first_year)
        rendering(selectedType)
        // Developing(FireBaseDatabase):
        DatabaseManager.toolsDBReference.addValueEventListener(object : ValueEventListener {
            // Method(OnDataChange):
            override fun onDataChange(snapshot: DataSnapshot) = rendering(selectedType)

            // Method(OnDataCancelled):
            override fun onCancelled(error: DatabaseError) = rendering(selectedType)
        })
        // Developing:
        binding.firstYearButton.setOnClickListener { rendering(getString(R.string.first_year)).also { selectedType = getString(R.string.first_year) } }
        binding.secYearButton.setOnClickListener { rendering(getString(R.string.sec_year)).also { selectedType = getString(R.string.sec_year) } }
        binding.thirdYearButton.setOnClickListener { rendering(getString(R.string.third_year)).also { selectedType = getString(R.string.third_year) } }
        binding.fourYearButton.setOnClickListener { rendering(getString(R.string.four_year)).also { selectedType = getString(R.string.four_year) } }
    }

    private fun rendering(filtrationType: String) {
        // Loading:
    //    progressToggle(true)
        // Coroutines:
        lifecycleScope.launchWhenCreated {
            // Sending:
            model.homeChannel.send(HomeIntentions.GetTools(filtrationType))
            // Collecting:
            model.state.collect {
                // Checking:
                when (it) {
                    // Getting:
                    is HomeViewStates.Tools -> {
                        // Initializing:
                        adapter = SpecialToolsAdapter(requireContext(), it.tools).also { progressToggle(false) }
                        // Checking:
                        if (it.tools.isEmpty()) progressToggle(true)
                        // Setting:
                        binding.specialToolsRecyclerView.adapter = adapter
                    }
                    is HomeViewStates.Failure -> progressToggle(true).also { Log.d(GeneralToolsFragment.TAG, "rendering: ${getString(R.string.failure_msg)}") }
                }
            }
        }
    }

    // Method(ProgressToggle):
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.specialToolsRecyclerView.visibility = if (visible) View.GONE else View.VISIBLE
        binding.specialToolsProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
}