package com.ar.team.company.schoolsupplies.ui.activities.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ar.team.company.schoolsupplies.control.adapter.MessageAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ActivityMessagesBinding
import com.ar.team.company.schoolsupplies.model.models.Message
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.firebase.auth.FirebaseAuth

class MessagesActivity : AppCompatActivity() {
    private val binding: ActivityMessagesBinding by lazy { ActivityMessagesBinding.inflate(layoutInflater) }
    private var messages :ArrayList<Message> = ArrayList()
    private lateinit var adapter: MessageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:
        binding.backButton.setOnClickListener { finish()}
        adapter = MessageAdapter(this, messages)
        // Setting:
        getDataMessages()

        binding.messagesRecyclerView.adapter = adapter

    }

    private fun getDataMessages() {
        DatabaseManager.messagesDBReference.get().addOnSuccessListener { it ->

            for (message in it.children) {
                // Adding:
                message.getValue(Message::class.java)?.let {
                    if(it.id.contains(FirebaseAuth.getInstance().currentUser!!.uid))
                    messages.add(it)

                }
            }
            binding.messagesProgress.visibility=View.GONE
            adapter.notifyDataSetChanged()

        }


    }

}