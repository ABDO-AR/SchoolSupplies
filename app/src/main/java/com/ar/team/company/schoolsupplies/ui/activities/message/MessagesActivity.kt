@file:Suppress("SameParameterValue")

package com.ar.team.company.schoolsupplies.ui.activities.message

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ar.team.company.schoolsupplies.control.adapter.MessageAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ActivityMessagesBinding
import com.ar.team.company.schoolsupplies.model.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessagesActivity : AppCompatActivity() {

    // Binding:
    private val binding: ActivityMessagesBinding by lazy { ActivityMessagesBinding.inflate(layoutInflater) }
    private var messages: ArrayList<Message> = ArrayList()

    // MessagesAdapter:
    private lateinit var adapter: MessageAdapter

    // Method(OnCreate):
    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:
        adapter = MessageAdapter(this, messages).also { progressToggle(false) }
        // Getting:
        getDataMessages()
        // Setting:
        binding.messagesRecyclerView.adapter = adapter
        // Developing:
        binding.backButton.setOnClickListener { finish() }
    }

    // Method(GetDataMessages):
    @SuppressLint("NotifyDataSetChanged")
    private fun getDataMessages() {
        // Developing:
        DatabaseManager.messagesDBReference.get().addOnSuccessListener { it ->
            // Looping(Children's):
            for (message in it.children) {
                // Adding:
                message.getValue(Message::class.java)?.let {
                    // Checking(IF MESSAGES CONTAINS CURRENT USER UID -> ADD MESSAGES):
                    if (message.key!!.contains(FirebaseAuth.getInstance().currentUser!!.uid)) messages.add(it)
                    // Toggling:
                    progressToggle(false)
                }
            }
            // Notifying:
            adapter.notifyDataSetChanged()
        }
    }

    // Method(ProgressToggle):
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.messagesRecyclerView.visibility = if (visible) View.GONE else View.VISIBLE
        binding.messagesProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }
}