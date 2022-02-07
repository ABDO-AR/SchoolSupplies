package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ToolItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.firebase.auth.FirebaseAuth

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

            if (tool.toolRequestID.contains(FirebaseAuth.getInstance().currentUser!!.uid))
                requestButton.text="ordered"
            else
                requestButton.text= context.getString(R.string.request_msg)

            if(tool.ownerID.trim() != FirebaseAuth.getInstance().currentUser!!.uid.trim())
                requestButton.visibility= View.VISIBLE
            else
                requestButton.visibility= View.GONE

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




            requestButton.setOnClickListener {

                if (!tool.toolRequestID.contains(FirebaseAuth.getInstance().currentUser!!.uid))
                {
                    tool.toolRequestID= FirebaseAuth.getInstance().currentUser!!.uid
                    DatabaseManager.toolsDBReference.child(tool.toolID).child("toolRequest").setValue( tool.toolRequestID)
                    var newTool=tool
                    newTool.toolID=DatabaseManager.ordersDBReference.push().key.toString()
                    newTool.toolRequestID= FirebaseAuth.getInstance().currentUser!!.uid

                    DatabaseManager.ordersDBReference.child(tool.ownerID).child(newTool.toolID).setValue(newTool)
                    var updateRequests = tool.toolRequestID+","+FirebaseAuth.getInstance().currentUser!!.uid

                    tools[position].toolRequestID=updateRequests

                    notifyDataSetChanged()
                }

            }
        }
    }

    override fun getItemCount(): Int = tools.size

    // Holder:
    inner class GeneralToolsViewHolder(val binding: ToolItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}