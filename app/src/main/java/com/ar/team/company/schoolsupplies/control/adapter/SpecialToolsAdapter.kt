package com.ar.team.company.schoolsupplies.control.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ToolItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Message
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
            if (tool.toolRequestID.contains(FirebaseAuth.getInstance().currentUser!!.uid))
                requestButton.text = context.getString(R.string.orderd_msg) else requestButton.text = context.getString(R.string.request_msg)

            if (tool.toolAcceptedIDs.contains(FirebaseAuth.getInstance().currentUser!!.uid))
            {
                stateButton.visibility=View.VISIBLE
                stateButton.text=context.getString(R.string.request_msg_accept)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    stateButton.setBackgroundColor(context.getColor(R.color.green_color))
                }

            }
            else if (tool.toolRejectedIDs.contains(FirebaseAuth.getInstance().currentUser!!.uid))
                stateButton.visibility=View.VISIBLE

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
                if (!tool.toolRequestID.contains(FirebaseAuth.getInstance().currentUser!!.uid)) {
                    // Initializing:
                    tool.toolRequestID = FirebaseAuth.getInstance().currentUser!!.uid
                    // Updating:
                    DatabaseManager.toolsDBReference.child(tool.toolID).child("toolRequestID").setValue(tool.toolRequestID)
                    // Initializing:
                    tool.toolBasicID=tool.toolID
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
                else
                {
                    // Initializing:
                    val builder = MaterialAlertDialogBuilder(context)
                    val name = EditText(context)
                    // Setting:
                    builder.setTitle(context.getString(R.string.send_message_to_msg) + userNameTextView.text)
                    builder.setNegativeButton(context.getString(R.string.cancel_msg), null)
                    name.hint = context.getString(R.string.set_your_msg)
                    builder.setView(name)
                    // Developing:
                    builder.setPositiveButton("Send") { _, _ ->
                        // Initializing:
                        val messageBody = name.text.toString()
                        // Checking:
                        if (messageBody.isEmpty()) Toast.makeText(context, context.getString(R.string.cannot_sent_empty_msg), Toast.LENGTH_SHORT).show()
                        else {
                            // Getting:
                            DatabaseManager.usersDBReference.child( FirebaseAuth.getInstance().currentUser!!.uid)
                                .get().addOnSuccessListener {
                                    // Initializing:
                                    val userMe = it.getValue(User::class.java)
                                    val id = FirebaseAuth.getInstance().currentUser!!.uid + " -- " + tool.ownerID
                                    val messageToSend = Message(id, messageBody, FirebaseAuth.getInstance().currentUser!!.uid,
                                        tool.ownerID, userMe!!.userName, userNameTextView.text.toString(), "")
                                    // Setting:
                                    DatabaseManager.messagesDBReference.child(id).setValue(messageToSend)
                                    // Showing:
                                    Toast.makeText(context, context.getString(R.string.has_been_sent_msg), Toast.LENGTH_SHORT).show()
                                }
                        }
                    }
                    // Building:
                    builder.show()
                    builder.create()
                }
                }
        }
    }

    override fun getItemCount(): Int = tools.size

    // Holder:
    inner class SpecialToolsViewHolder(val binding: ToolItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}