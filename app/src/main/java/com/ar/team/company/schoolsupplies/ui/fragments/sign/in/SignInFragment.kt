package com.ar.team.company.schoolsupplies.ui.fragments.sign.`in`

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.databinding.FragmentSignInBinding
import com.ar.team.company.schoolsupplies.ui.activitys.sign.SignViewModel

class SignInFragment : Fragment() {

    // Fields:
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!

    // ViewModel:
    private val model: SignViewModel by lazy { ViewModelProvider(this)[SignViewModel::class.java] }

    // NavController:
    private val controller: NavController by lazy { Navigation.findNavController(requireActivity(), R.id.host_container) }

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "SignInFragment"
    }

    // Method(OnCreateView):
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Initializing:
        _binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        // Returning:
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        // Initializing:
        binding.signUpTextView.setOnClickListener { controller.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()) }
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
}