package com.ar.team.company.schoolsupplies.ui.activities.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ar.team.company.schoolsupplies.control.adapter.MessageAdapter
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ActivityMessagesBinding
import com.ar.team.company.schoolsupplies.model.models.Message
import com.ar.team.company.schoolsupplies.model.models.User

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
        adapter = MessageAdapter(this, messages).also { progressToggle(false) }
        // Setting:
        getDataMessages()

        binding.rvListUser.adapter = adapter

    }

    private fun getDataMessages() {
        DatabaseManager.messagesDBReference.get().addOnSuccessListener { it ->

            for (message in it.children) {
                // Adding:
                message.getValue(Message::class.java)?.let {
                    messages.add(it)

                }
            }
            adapter.notifyDataSetChanged()

        }


    }
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.rvListUser.visibility = if (visible) View.GONE else View.VISIBLE
        binding.generalToolsProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }
}