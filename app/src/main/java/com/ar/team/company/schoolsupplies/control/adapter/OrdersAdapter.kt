package com.ar.team.company.schoolsupplies.control.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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

                messageButton.setOnClickListener {
                    //setup AlertDialog
                    val builder = MaterialAlertDialogBuilder(context)
                    //set message in alertDialog
                    builder.setTitle("send message to "+user.userName)
                    //set when click Cancel
                    builder.setNegativeButton("Cancel", null)
                    // init Edit text
                    val name = EditText(context)
                    name.hint = "set your message"

                    // build edit Text in alert
                    // build edit Text in alert
                    builder.setView(name)
                    // when click update
                    // when click update
                    builder.setPositiveButton("Send") { dialog, which ->
                        val message = name.text.toString()
                        //   product.setNameProduct(newName);
                        // Helper.productsList.get(position).setNameProduct(newName);
                        if (message.isEmpty())
                            Toast.makeText(
                            context,
                            "it cannot be sent empty !!, please write message",
                            Toast.LENGTH_SHORT
                        ).show()
                        else
                        {
                            // Getting:
                            DatabaseManager.usersDBReference.child(tool.ownerID).get().addOnSuccessListener {
                                // Initializing:
                                val userMe = it.getValue(User::class.java)


                            var id = FirebaseAuth.getInstance().currentUser!!.uid +" -- "+user.id
                            var message = Message(id, message  ,FirebaseAuth.getInstance().currentUser!!.uid ,user.id
                                    ,
                                userMe!!.userName ,user.userName,user.userImage )
                            DatabaseManager.messagesDBReference.child(id).setValue(message)
                            Toast.makeText(
                                context,
                                "has been sent ",
                                Toast.LENGTH_SHORT
                            ).show()
                            }
                        }


                    }
                    builder.show()
                    builder.create()

                }

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