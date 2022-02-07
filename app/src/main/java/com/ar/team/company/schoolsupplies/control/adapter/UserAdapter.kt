package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.UserlItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User

class UserAdapter(private val context: Context, private val users: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.GeneralToolsViewHolder>() {

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralToolsViewHolder {
        // Initializing:
        val inflater = LayoutInflater.from(context)
        val binding = UserlItemViewBinding.inflate(inflater, parent, false)
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
    inner class GeneralToolsViewHolder(val binding: UserlItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}