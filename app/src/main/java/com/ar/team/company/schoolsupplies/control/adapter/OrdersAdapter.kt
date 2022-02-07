package com.ar.team.company.schoolsupplies.control.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.OrderItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Message
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class OrdersAdapter(private val context: Context, private val tools: ArrayList<Tool>) : RecyclerView.Adapter<OrdersAdapter.GeneralToolsViewHolder>() {

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralToolsViewHolder {
        // Initializing:
        val inflater = LayoutInflater.from(context)
        val binding = OrderItemViewBinding.inflate(inflater, parent, false)
        // Returning:
        return GeneralToolsViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: GeneralToolsViewHolder, position: Int) {
        // Initializing:
        val tool = tools[position]
        // Prepare:
        holder.binding.apply {
            // Getting:
            DatabaseManager.usersDBReference.child(tool.toolRequestID).get().addOnSuccessListener { it ->
                // Initializing:
                val user = it.getValue(User::class.java)
                // Checking:
                if (user!!.userImage != User.NO_USER_IMAGE) userImageView.setImageBitmap(user.decode())
                // Setting:
                userNameTextView.text = user.userName
                // Developing:
                messageButton.setOnClickListener {
                    // Initializing:
                    val builder = MaterialAlertDialogBuilder(context)
                    val name = EditText(context)
                    // Setting:
                    builder.setTitle(context.getString(R.string.send_message_to_msg) + user.userName)
                    builder.setNegativeButton(context.getString(R.string.cancel_msg), null)
                    name.hint = context.getString(R.string.set_your_msg)
                    builder.setView(name)
                    // Developing:
                    builder.setPositiveButton("Send") { _, _ ->
                        // Initializing:
                        val message = name.text.toString()
                        // Checking:
                        if (message.isEmpty()) Toast.makeText(context, context.getString(R.string.cannot_sent_empty_msg), Toast.LENGTH_SHORT).show()
                        else {
                            // Getting:
                            DatabaseManager.usersDBReference.child(tool.ownerID).get().addOnSuccessListener {
                                // Initializing:
                                val userMe = it.getValue(User::class.java)
                                val id = FirebaseAuth.getInstance().currentUser!!.uid + " -- " + user.id
                                val messageToSend = Message(id, message, FirebaseAuth.getInstance().currentUser!!.uid, user.id, userMe!!.userName, user.userName, user.userImage)
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
            // Setting:
            toolImageView.setImageBitmap(tool.decode())
            requestTextView.text = tool.details
            // Developing:
            acceptButton.setOnClickListener {
                // Setting:
                DatabaseManager.ordersDBReference.child(tool.ownerID).child(tool.toolID).removeValue()
                // Removing:
                tools.remove(tool)
                // Notifying:
                notifyDataSetChanged()
            }
            cancelButton.setOnClickListener {
                // Setting:
                DatabaseManager.ordersDBReference.child(tool.ownerID).child(tool.toolID).removeValue()
                // Removing:
                tools.remove(tool)
                // Notifying:
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = tools.size

    // Holder:
    inner class GeneralToolsViewHolder(val binding: OrderItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}