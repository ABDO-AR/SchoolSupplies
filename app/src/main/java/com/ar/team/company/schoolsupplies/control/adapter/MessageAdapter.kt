package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.MessageItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Message

import com.ar.team.company.schoolsupplies.model.models.User
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context: Context, private val messages: ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.GeneralToolsViewHolder>() {

    // Adapter:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralToolsViewHolder {
        // Initializing:
        val inflater = LayoutInflater.from(context)
        val binding = MessageItemViewBinding.inflate(inflater, parent, false)
        // Returning:
        return GeneralToolsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GeneralToolsViewHolder, position: Int) {
        // Initializing:
        val tool = messages[position]

        // Prepare:
        holder.binding.apply {
            var userName="";
            var id="";

            var message = messages[position]
                // Setting:
          //      if (message!!.userSenderImage != User.NO_USER_IMAGE) userImageView.setImageBitmap(message.decode())

                if (FirebaseAuth.getInstance().currentUser!!.uid == message.userSenderID)
                {
                    userName= message.userReceiverName
                    id=message.userReceiverID
                }
                   else
                {
                    userName=  message.userSenderName
                    id=message.userSenderID
                }


                  userNameTextView.text =userName

            messageTextView.text=message.message


            layout.setOnClickListener {
                // Initializing:
                val builder = MaterialAlertDialogBuilder(context)
                val name = EditText(context)
                // Setting:
                builder.setTitle(context.getString(R.string.send_message_to_msg) + userName)
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
                            val id = FirebaseAuth.getInstance().currentUser!!.uid + " -- " + message.userReceiverID
                            val messageToSend = Message(id, messageBody, FirebaseAuth.getInstance().currentUser!!.uid,
                                message.userReceiverID, userMe!!.userName, message.userReceiverName, message.userSenderImage)
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




    override fun getItemCount(): Int = messages.size

    // Holder:
    inner class GeneralToolsViewHolder(val binding: MessageItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}
