package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.databinding.MessageItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Message

import com.ar.team.company.schoolsupplies.model.models.User
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

            var message = messages[position]
                // Setting:
                if (message!!.userSenderImage != User.NO_USER_IMAGE) userImageView.setImageBitmap(message.decode())

                if (FirebaseAuth.getInstance().currentUser!!.uid == message.userSenderID)
                    userNameTextView.text = message.userReceiverName

            else
                    userNameTextView.text = message.userSenderName

            messageTextView.text=message.message


            }


        }




    override fun getItemCount(): Int = messages.size

    // Holder:
    inner class GeneralToolsViewHolder(val binding: MessageItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}
