package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.OrderItemViewBinding
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User
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

    override fun onBindViewHolder(holder: GeneralToolsViewHolder, position: Int) {
        // Initializing:
        val tool = tools[position]

        // Prepare:
        holder.binding.apply {



            // Getting:
            DatabaseManager.usersDBReference.child(tool.toolRequestID).get().addOnSuccessListener {
                // Initializing:
                val user = it.getValue(User::class.java)
                // Setting:
                if (user!!.userImage != User.NO_USER_IMAGE) userImageView.setImageBitmap(user.decode())
                userNameTextView.text = user.userName

            }
            // Setting:
            toolImageView.setImageBitmap(tool.decode())

            requestTextView.text = tool.details



            acceptButton.setOnClickListener {
                DatabaseManager.ordersDBReference.child(tool.ownerID).child(tool.toolID).removeValue()
                tools.remove(tool)

                notifyDataSetChanged()


            }

            cancelButton.setOnClickListener {

                DatabaseManager.ordersDBReference.child(tool.ownerID).child(tool.toolID).removeValue()
                tools.remove(tool)

                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = tools.size

    // Holder:
    inner class GeneralToolsViewHolder(val binding: OrderItemViewBinding) : RecyclerView.ViewHolder(binding.root)
}