package com.ar.team.company.schoolsupplies.control.repository

import android.util.Base64
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
        auth.signInWithEmailAndPassword(intention.email, intention.password)
            .addOnFailureListener { sign(false) }.addOnSuccessListener { sign(true) }
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
            manager.usersDBReference.child(user!!.id).setValue(user)
                .addOnSuccessListener { sign(true) }.addOnFailureListener { sign(false) }
        }.addOnFailureListener { sign(false) }
    }

    // Method(UploadTool):
    override suspend fun uploadTool(intention: AddToolIntentions.Upload, tool: (success: Boolean) -> Unit) {
        // Uploading into storage
        val path = "https://firebasestorage.googleapis.com/v0/b/schoolsupplies-b9361.appspot.com/o/tools_images%2F${intention.tool.name}_${intention.tool.ownerID}.jpeg?alt=media&token=0ccb5bb6-9fa9-4a7e-9986-afbebac78bf3"
        val storage = manager.storage.getReference(DatabaseManager.TOOLS_IMAGES_FOLDER_NAME).child("${intention.tool.name}_${intention.tool.ownerID}.jpeg")
        val bytes = Base64.decode(intention.tool.toolImage, Base64.DEFAULT)!!
        // Storage:
        storage.putBytes(bytes).addOnSuccessListener {
            intention.tool.toolImage = path
            // Uploading:
            manager.toolsDBReference.child(intention.tool.toolID).setValue(intention.tool)
                .addOnSuccessListener { tool(true) }
                .addOnFailureListener { tool(false) }
        }.addOnFailureListener { tool(false) }
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