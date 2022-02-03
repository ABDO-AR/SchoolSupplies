package com.ar.team.company.schoolsupplies.ui.fragments.sign.`in`

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.databinding.FragmentSignInBinding
import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.ar.team.company.schoolsupplies.model.states.SignViewStates
import com.ar.team.company.schoolsupplies.ui.activitys.home.HomeActivity
import com.ar.team.company.schoolsupplies.ui.activitys.sign.SignViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    // Fields:
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!

    // ViewModel:
    private val model: SignViewModel by viewModels()

    // FirebaseAuth:
    @Inject
    lateinit var auth: FirebaseAuth

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
        binding.signInButton.setOnClickListener { submit(binding.emailEditText.text.toString(), binding.passwordEditText.text.toString()) }
    }

    // Method(Submit):
    private fun submit(email: String, password: String) {
        // Checking:
        when {
            // Checking:
            TextUtils.isEmpty(email) -> Snackbar.make(binding.root, R.string.email_is_empty, Snackbar.LENGTH_SHORT).show()
            password.length < 5 -> Snackbar.make(binding.root, R.string.password_is_empty, Snackbar.LENGTH_SHORT).show()
            // Coroutines:
            else -> lifecycleScope.launchWhenCreated {
                // Submitting:
                model.signChannel.send(SignIntentions.SignIn(email, password))
                // Enabling(Progress):
                progressToggle(true)
                // Collecting:
                model.state.collect {
                    // Checking:
                    when (it) {
                        // Singing:
                        is SignViewStates.Success -> progressToggle(false).also { homeActivity() }
                        is SignViewStates.Failure -> progressToggle(false).also { if (auth.currentUser !== null) homeActivity() else Log.d(TAG, "submit: ${getString(R.string.error_create_user)}") }
                    }
                }
            }
        }
    }

    // Method(HomeActivity):
    private fun homeActivity() = startActivity(Intent(requireActivity(), HomeActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

    // Method(ProgressToggle):
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.signInButton.visibility = if (visible) View.GONE else View.VISIBLE
        binding.signInProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
}