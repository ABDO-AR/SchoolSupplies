package com.ar.team.company.schoolsupplies.ui.fragments.sign.`in`

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.databinding.FragmentSignInBinding
import com.ar.team.company.schoolsupplies.models.User
import com.ar.team.company.schoolsupplies.ui.activitys.home.HomeActivity
import com.ar.team.company.schoolsupplies.ui.activitys.sign.SignViewModel
import com.ar.team.company.schoolsupplies.ui.fragments.tools.Helper
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SignInFragment : Fragment() {

    // Fields:
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!
    private var auth: FirebaseAuth? = null
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

        // init Firebase Auth
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        // Initializing:
        binding.signUpTextView.setOnClickListener { controller.navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment()) }
        binding.signUpButton.setOnClickListener {
            signInWithEmail( binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
        }
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
    private fun signInWithEmail(email: String, password: String) {
        when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(requireContext(), R.string.email_is_empty, Toast.LENGTH_SHORT).show()
            }
            password.length < 5 -> {
                Toast.makeText(requireContext(), R.string.password_is_empty, Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {

                auth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        requireActivity(),
                        OnCompleteListener { task: Task<AuthResult> ->
                            if (task.isSuccessful) {
                                val id = task.result!!.user!!.uid
                                startActivity(Intent(requireActivity(), HomeActivity::class.java).
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

                            } else {

                                    Toast.makeText(
                                    requireActivity(),
                                    "error :" + task.exception!!.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
                    .addOnFailureListener(OnFailureListener { e: Exception? -> })
            }
        }
    }




}