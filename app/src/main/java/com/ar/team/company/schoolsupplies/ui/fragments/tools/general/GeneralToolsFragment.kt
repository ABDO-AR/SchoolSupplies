package com.ar.team.company.schoolsupplies.ui.fragments.tools.general

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.adapter.GeneralToolsAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.FragmentGeneralToolsBinding
import com.ar.team.company.schoolsupplies.model.intentions.HomeIntentions
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.states.HomeViewStates
import com.ar.team.company.schoolsupplies.ui.activities.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GeneralToolsFragment : Fragment() {

    // Fields:
    private var _binding: FragmentGeneralToolsBinding? = null
    private val binding: FragmentGeneralToolsBinding get() = _binding!!

    // ViewModel:
    private val model: HomeViewModel by viewModels()
     private  var tools : ArrayList<Tool> = ArrayList()
    // Adapter:
    private lateinit var adapter: GeneralToolsAdapter

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "GeneralToolsFragment"
    }

    // Method(OnCreateView):
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Initializing:
        _binding = FragmentGeneralToolsBinding.inflate(layoutInflater, container, false)


        // Returning:
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        // Initializing:

       rendering()
        // Developing:
        DatabaseManager.toolsDBReference.addValueEventListener(object : ValueEventListener {
            // Method(OnDataChange):
            override fun onDataChange(snapshot: DataSnapshot) = rendering()

            // Method(OnDataCancelled):
            override fun onCancelled(error: DatabaseError) = rendering()
        })

    }

   private fun rendering() {
        // Loading:
 //  progressToggle(true)
        // Coroutines:
        lifecycleScope.launchWhenCreated {
            // Sending:
            model.homeChannel.send(HomeIntentions.GetTools(getString(R.string.general_tools_tab_name)))
            // Collecting:
            model.state.collect {
                // Checking:
                when (it) {
                    // Getting:
                    is HomeViewStates.Tools -> {
                        // Initializing:
                        adapter = GeneralToolsAdapter(requireContext(), it.tools).also { progressToggle(false) }
                        // Checking:
                        if (it.tools.isEmpty()) progressToggle(true)
                        // Setting:
                        binding.generalToolsRecyclerView.adapter = adapter
                    }
                    is HomeViewStates.Failure -> progressToggle(true).also { Log.d(TAG, "rendering: ${getString(R.string.failure_msg)}") }
                }
            }
        }
    }

    // Method(ProgressToggle):
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.generalToolsRecyclerView.visibility = if (visible) View.GONE else View.VISIBLE
        binding.generalToolsProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }


}