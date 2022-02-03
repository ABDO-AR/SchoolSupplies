package com.ar.team.company.schoolsupplies.control.interfaces

import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface SignRepo {

    // Method(SignIn):
    fun signIn(intention: SignIntentions.SignIn): Task<AuthResult>

    // Method(SignUp)
    fun signUp(intention: SignIntentions.SignUp): Task<Void>?
}