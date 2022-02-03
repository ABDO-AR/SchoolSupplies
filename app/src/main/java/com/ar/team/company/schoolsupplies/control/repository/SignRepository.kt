package com.ar.team.company.schoolsupplies.control.repository

import com.ar.team.company.schoolsupplies.control.interfaces.SignRepo
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.ar.team.company.schoolsupplies.model.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class SignRepository(private val auth: FirebaseAuth, private val manager: DatabaseManager) : SignRepo {

    // Method(SignIn):
    override fun signIn(intention: SignIntentions.SignIn): Task<AuthResult> {
        // Initializing:
        var isSuccess = false
        // Returning:
        return auth.signInWithEmailAndPassword(intention.email, intention.password).addOnSuccessListener { isSuccess = true }
    }

    // Method(SignUp):
    override fun signUp(intention: SignIntentions.SignUp): Task<Void>? {
        // Initializing:
        var user: User?
        var task: Task<Void>? = null
        // Creating:
        auth.createUserWithEmailAndPassword(intention.email, intention.password).addOnSuccessListener {
            // Initializing:
            user = User(it.user!!.uid, intention.userName, intention.schoolName, intention.currentYear, intention.email, intention.password, intention.phoneNumber, intention.address)
            // Uploading:
            task = manager.usersDBReference.child(user!!.id).setValue(user).addOnSuccessListener { }
            // Returning:
            return@addOnSuccessListener
        }
        // Returning:
        return task
    }
}