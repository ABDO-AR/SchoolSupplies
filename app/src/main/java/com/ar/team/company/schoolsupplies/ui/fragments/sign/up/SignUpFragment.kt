package com.ar.team.company.schoolsupplies.ui.fragments.sign.up

import android.app.ProgressDialog
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
import com.ar.team.company.schoolsupplies.control.interfaces.LoadingDialog
import com.ar.team.company.schoolsupplies.databinding.FragmentSignUpBinding
import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.ar.team.company.schoolsupplies.model.states.SignViewStates
import com.ar.team.company.schoolsupplies.ui.activities.home.HomeActivity
import com.ar.team.company.schoolsupplies.ui.activities.sign.SignViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    // Fields:
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    private lateinit var  loading: LoadingDialog;
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
        const val TAG: String = "SignUpFragment"
    }

    // Method(OnCreateView):
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Initializing:
        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        // Returning:
        loading = LoadingDialog(requireActivity())
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        // Initializing:
        binding.signInTextView.setOnClickListener { controller.navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()) }
        binding.signUpButton.setOnClickListener {
            // Submitting:
            submit(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.currentYearEditText.text.toString(),
                binding.nameEditText.text.toString(),
                binding.schoolNameEditText.text.toString(),
                binding.phoneNumberEditText.text.toString(),
                binding.addressEditText.text.toString()
            )
        }
    }

    // Method(Submit):
    private fun submit(email: String, password: String, currentYear: String, userName: String, schoolName: String, phoneNumber: String, address: String) {
        // Checking:

        when {
            // Checking:
            TextUtils.isEmpty(email) -> Snackbar.make(
                binding.root,
                R.string.email_is_empty,
                Snackbar.LENGTH_SHORT
            ).show()
            password.length < 5 -> Snackbar.make(
                binding.root,
                R.string.password_is_empty,
                Snackbar.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(userName) -> Snackbar.make(
                binding.root,
                R.string.user_is_empty,
                Snackbar.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(currentYear) -> Snackbar.make(
                binding.root,
                R.string.year_is_empty,
                Snackbar.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(phoneNumber) -> Snackbar.make(
                binding.root,
                R.string.phone_is_empty,
                Snackbar.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(schoolName) -> Snackbar.make(
                binding.root,
                R.string.school_is_empty,
                Snackbar.LENGTH_SHORT
            ).show()
            TextUtils.isEmpty(address) -> Snackbar.make(
                binding.root,
                R.string.address_is_empty,
                Snackbar.LENGTH_SHORT
            ).show()
            // Coroutines:
            else -> {
                loading.startLoading()

                lifecycleScope.launchWhenCreated {
                // Submitting:
                model.signChannel.send(
                    SignIntentions.SignUp(
                        email,
                        password,
                        currentYear,
                        userName,
                        schoolName,
                        phoneNumber,
                        address
                    )
                )
                // Enabling(Progress):
                progressToggle(true)
                // Collecting:
                model.state.collect {
                    // Checking:
                    when (it) {
                        // Singing:
                        is SignViewStates.Success -> progressToggle(false).also {

                            homeActivity() }
                        is SignViewStates.Failure -> progressToggle(false).also {

                            if (auth.currentUser !== null) homeActivity() else {
                                Log.d(
                                    TAG,
                                    "submit: ${getString(R.string.error_create_user)}"
                                )
                                loading.isDismiss()
                                Snackbar.make(binding.root, getString(R.string.error_create_user), Snackbar.LENGTH_SHORT).show()
                            }
                        }
                    }
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
        binding.signUpButton.visibility = if (visible) View.GONE else View.VISIBLE
        //binding.signUpProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
}

