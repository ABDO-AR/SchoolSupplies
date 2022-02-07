package com.ar.team.company.schoolsupplies.ui.activities.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.ar.team.company.schoolsupplies.control.adapter.UserAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ActivitySearchBinding
import com.ar.team.company.schoolsupplies.model.models.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    // Fields:

    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private var users :ArrayList<User> = ArrayList()
    private lateinit var adapter: UserAdapter


    // Method(OnCreate):
    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:


        adapter = UserAdapter(this, users).also { progressToggle(false) }
        // Setting:

        binding.rvListUser.adapter = adapter
        getDataUsers()
        // Returning:
        binding.etSearch.addTextChangedListener {
            val newText = it.toString()
            if (newText.isNotEmpty()) {
                val newUsers: ArrayList<User> = ArrayList()
                for (user in users) {
                    if (user.userName.contains(newText) || user.userName.toLowerCase()
                            .contains(newText)
                    ) {
                        newUsers.add(0, user)
                        adapter = UserAdapter(this, newUsers).also { progressToggle(false) }                    }
                    binding.rvListUser.adapter = adapter
                }
            } else

            {
                adapter = UserAdapter(this, users).also { progressToggle(false) }
                // Setting
                binding.rvListUser.adapter = adapter
            }
        }


binding.backButton.setOnClickListener { finish()}
    }

    private fun getDataUsers() {
        DatabaseManager.usersDBReference.get().addOnSuccessListener {

            for (user in it.children) {
                // Adding:
                user.getValue(User::class.java)?.let { users.add(it) }
            }

            adapter.notifyDataSetChanged()

        }


    }
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.rvListUser.visibility = if (visible) View.GONE else View.VISIBLE
        binding.generalToolsProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }
}