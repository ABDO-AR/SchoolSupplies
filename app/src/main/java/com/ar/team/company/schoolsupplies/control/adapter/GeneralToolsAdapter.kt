package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ToolItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User

class GeneralToolsAdapter(private val context: Context, private val tools: ArrayList<Tool>) : RecyclerView.Adapter<GeneralToolsAdapter.GeneralToolsViewHolder>() {

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralToolsViewHolder {
        // Initializing:
        val inflater = LayoutInflater.from(context)
        val binding = ToolItemViewBinding.inflate(inflater, parent, false)
        // Returning:
        return GeneralToolsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GeneralToolsViewHolder, position: Int) {
        // Initializing:
        val tool = tools[position]
        // Prepare:
        holder.binding.apply {
            // Getting:
            DatabaseManager.usersDBReference.child(tool.ownerID).get().addOnSuccessListener {
                // Initializing:
                val owner = it.getValue(User::class.java)
                // Setting:
                if (owner!!.userImage != User.NO_USER_IMAGE) userImageView.setImageBitmap(owner.decode())
                userNameTextView.text = owner.userName
            }
            // Setting:
            toolImageView.setImageBitmap(tool.decode())
            toolDescriptionTextView.text = tool.details
        }
    }

    override fun getItemCount(): Int = tools.size

    // Holder:
    inner class GeneralToolsViewHolder(val binding: ToolItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}