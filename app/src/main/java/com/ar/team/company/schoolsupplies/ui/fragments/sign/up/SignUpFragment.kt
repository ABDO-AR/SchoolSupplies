package com.ar.team.company.schoolsupplies.ui.fragments.sign.up

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
import com.ar.team.company.schoolsupplies.databinding.FragmentSignUpBinding
import com.ar.team.company.schoolsupplies.models.User
import com.ar.team.company.schoolsupplies.ui.activitys.home.HomeActivity
import com.ar.team.company.schoolsupplies.ui.activitys.sign.SignViewModel
import com.ar.team.company.schoolsupplies.ui.fragments.tools.Helper
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    // Fields:
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    // ViewModel:
    private val model: SignViewModel by lazy { ViewModelProvider(this)[SignViewModel::class.java] }
  private var auth: FirebaseAuth? = null
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

        // init Firebase Auth
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    // Method(OnViewCreated):
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Here we will do our work.
        // Super:
        super.onViewCreated(view, savedInstanceState)
        // Initializing:
        binding.signInTextView.setOnClickListener { controller.navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()) }
        binding.signUpButton.setOnClickListener {
            //get Email and password from EditText

            //get Email and password from EditText


            submit(binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.currentYearEditText.text.toString(),
                binding.nameEditText.text.toString(),
                binding.schoolNameEditText.text.toString()
                ,  binding.phoneNumberEditText.text.toString()
                ,  binding.addressEditText.text.toString()
            )

        }
    }

    // Method(OnDestroyView):
    override fun onDestroyView() { // This will destroy our binding after destroying all views.
        // Super:
        super.onDestroyView()
        // Destroying:
        _binding = null
    }
    private fun submit(email: String, password: String, currentYear: String, userName: String, nameSchool: String ,phoneNumber: String, address: String) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(requireContext(), R.string.email_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 5) {
            Toast.makeText(requireContext(), R.string.password_is_empty, Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (TextUtils.isEmpty(currentYear)) {
            Toast.makeText(requireContext(), R.string.year_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(requireContext(), R.string.user_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(nameSchool)) {
            Toast.makeText(requireContext(), R.string.school_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(requireContext(), R.string.phone_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(requireContext(), R.string.address_is_empty, Toast.LENGTH_SHORT).show()
            return
        }
         else {
            auth!!.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (!task.isSuccessful) {
                       Toast.makeText(
                            requireContext(), "error. " + task.exception,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        createUser(
                            User(
                                auth!!.currentUser!!.uid,
                                email,
                                password,
                                userName,
                                nameSchool,
                                currentYear,
                                phoneNumber,
                                address,
                                ""

                            )
                        )
                    }

                }
        }
    }

    private fun createUser(user: User)
    {
        Helper.usersRef.child(user.id.toString()).setValue(user).addOnSuccessListener {

            // get user data and store
            Toast.makeText(
                requireActivity(),
                "create User Account Successfully",
                Toast.LENGTH_SHORT
            )
                .show()

            startActivity(Intent(requireActivity(), HomeActivity::class.java).
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }.addOnFailureListener { e ->
            Toast.makeText(
                requireActivity(),
                R.string.error_create_user,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    }

