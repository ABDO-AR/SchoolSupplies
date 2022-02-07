package com.ar.team.company.schoolsupplies.control.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ToolItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.firebase.auth.FirebaseAuth

class SpecialToolsAdapter(private val context: Context, private val tools: ArrayList<Tool>) : RecyclerView.Adapter<SpecialToolsAdapter.SpecialToolsViewHolder>() {

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialToolsViewHolder {
        // Initializing:
        val inflater = LayoutInflater.from(context)
        val binding = ToolItemViewBinding.inflate(inflater, parent, false)
        // Returning:
        return SpecialToolsViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SpecialToolsViewHolder, position: Int) {
        // Initializing:
        val tool = tools[position]
        // Prepare:
        holder.binding.apply {
            // Checking
            if (tool.toolRequestID.contains(FirebaseAuth.getInstance().currentUser!!.uid)) requestButton.text = context.getString(R.string.orderd_msg)
            else requestButton.text = context.getString(R.string.request_msg)
            // Checking:
            if (tool.ownerID.trim() != FirebaseAuth.getInstance().currentUser!!.uid.trim()) requestButton.visibility = View.VISIBLE
            else requestButton.visibility = View.GONE
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
            detailsTextView.text = tool.name
            // Developing::
            requestButton.setOnClickListener {
                // Checking:
                if (!tool.toolRequestID.contains(FirebaseAuth.getInstance().currentUser!!.uid)) {
                    // Initializing:
                    tool.toolRequestID = FirebaseAuth.getInstance().currentUser!!.uid
                    // Updating:
                    DatabaseManager.toolsDBReference.child(tool.toolID).child("toolRequest").setValue(tool.toolRequestID)
                    // Initializing:
                    tool.toolID = DatabaseManager.ordersDBReference.push().key.toString()
                    tool.toolRequestID = FirebaseAuth.getInstance().currentUser!!.uid
                    // Updating:
                    DatabaseManager.ordersDBReference.child(tool.ownerID).child(tool.toolID).setValue(tool)
                    // Initializing:
                    val updateRequests = tool.toolRequestID + "," + FirebaseAuth.getInstance().currentUser!!.uid
                    tools[position].toolRequestID = updateRequests
                    // Notifying:
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount(): Int = tools.size

    // Holder:
    inner class SpecialToolsViewHolder(val binding: ToolItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}