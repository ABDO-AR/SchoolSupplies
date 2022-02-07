package com.ar.team.company.schoolsupplies.control.repository

import com.ar.team.company.schoolsupplies.control.interfaces.MainRepo
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.model.intentions.AddToolIntentions
import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot

class MainRepository(private val auth: FirebaseAuth, private val manager: DatabaseManager) : MainRepo {

    // Method(SignIn):
    override suspend fun signIn(intention: SignIntentions.SignIn, sign: (success: Boolean) -> Unit) {
        // Signing:
        auth.signInWithEmailAndPassword(intention.email, intention.password).addOnSuccessListener { sign(true) }
    }

    // Method(SignUp):
    override suspend fun signUp(intention: SignIntentions.SignUp, sign: (success: Boolean) -> Unit) {
        // Initializing:
        var user: User?
        // Creating:
        auth.createUserWithEmailAndPassword(intention.email, intention.password).addOnSuccessListener {
            // Initializing:
            user = User(it.user!!.uid, intention.userName, intention.schoolName, intention.currentYear, intention.email, intention.password, intention.phoneNumber, intention.address)
            // Uploading:
            manager.usersDBReference.child(user!!.id).setValue(user).addOnSuccessListener { sign(true) }
        }
    }

    // Method(UploadTool):
    override suspend fun uploadTool(intention: AddToolIntentions.Upload, tool: (success: Boolean) -> Unit) {
        // Uploading:
        manager.toolsDBReference.child(intention.tool.toolID).setValue(intention.tool).addOnSuccessListener { tool(true) }
    }

    // Method(GetTools):
    override suspend fun getTools(tools: (tools: ArrayList<Tool>) -> Unit) {
        // Getting:
        manager.toolsDBReference.get().addOnSuccessListener { tools(returnTools(it)) }
    }

    // Method(ReturnTools):
    private fun returnTools(snapshot: DataSnapshot): ArrayList<Tool> {
        // Initializing:
        val tools: ArrayList<Tool> = ArrayList()
        // Getting:
        for (tool in snapshot.children) {
            // Adding:
            tool.getValue(Tool::class.java)?.let { tools.add(it) }
        }
        // Returning:
        return tools
    }
}