@file:Suppress("SameParameterValue")

package com.ar.team.company.schoolsupplies.ui.activities.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.ar.team.company.schoolsupplies.control.adapter.UserAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ActivitySearchBinding
import com.ar.team.company.schoolsupplies.model.models.User
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    // Binding:
    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private var users: ArrayList<User> = ArrayList()

    // UserAdapter:
    private lateinit var adapter: UserAdapter

    // Method(OnCreate):
    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:
        adapter = UserAdapter(this, users).also { progressToggle(false) }
        // Setting:
        binding.usersRecyclerView.adapter = adapter
        getDataUsers()
        // Developing:=:
        binding.backButton.setOnClickListener { finish() }
        binding.etSearch.addTextChangedListener { text ->
            // Initializing:
            adapter = if (text.toString().isNotEmpty()) UserAdapter(this, users.filter { it.userName.lowercase().contains(text.toString().lowercase()) } as ArrayList<User>)
            else UserAdapter(this, users)
            // Toggling:
            progressToggle(false)
            // Setting:
            binding.usersRecyclerView.adapter = adapter
        }
    }

    // Method(GetUserData):
    @SuppressLint("NotifyDataSetChanged")
    private fun getDataUsers() {
        // Getting:
        DatabaseManager.usersDBReference.get().addOnSuccessListener { it ->
            // Looping:
            for (user in it.children) {
                // Adding:
                user.getValue(User::class.java)?.let { users.add(it) }
            }
            // Notifying:
            adapter.notifyDataSetChanged()
            // Toggling:
            progressToggle(false)
        }
    }

    // Method(ProgressToggle):
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.usersRecyclerView.visibility = if (visible) View.GONE else View.VISIBLE
        binding.usersProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }
}