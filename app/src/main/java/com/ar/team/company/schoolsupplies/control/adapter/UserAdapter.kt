package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.UserItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Message
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(private val context: Context, private val users: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.GeneralToolsViewHolder>() {

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralToolsViewHolder {
        // Initializing:
        val inflater = LayoutInflater.from(context)
        val binding = UserItemViewBinding.inflate(inflater, parent, false)
        // Returning:
        return GeneralToolsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GeneralToolsViewHolder, position: Int) {
        // Initializing:
        val tool = users[position]

        // Prepare:
        holder.binding.apply {

            var user = users[position]
                // Setting:
                if (user!!.userImage != User.NO_USER_IMAGE) userImageView.setImageBitmap(user.decode())
                userNameTextView.text = user.userName


        }


    }

    override fun getItemCount(): Int = users.size

    // Holder:
    inner class GeneralToolsViewHolder(val binding: UserItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}