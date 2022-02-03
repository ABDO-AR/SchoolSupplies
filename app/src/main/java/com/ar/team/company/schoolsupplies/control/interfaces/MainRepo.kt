package com.ar.team.company.schoolsupplies.control.interfaces

import com.ar.team.company.schoolsupplies.model.intentions.AddToolIntentions
import com.ar.team.company.schoolsupplies.model.intentions.SignIntentions
import com.ar.team.company.schoolsupplies.model.models.Tool

interface MainRepo {

    // Method(SignIn):
    suspend fun signIn(intention: SignIntentions.SignIn, sign: (success: Boolean) -> Unit)

    // Method(SignUp)
    suspend fun signUp(intention: SignIntentions.SignUp, sign: (success: Boolean) -> Unit)

    // Method(UploadTool):
    suspend fun uploadTool(intention: AddToolIntentions.Upload, tool: (success: Boolean) -> Unit)

    // Method(GetTools):
    suspend fun getTools(tools: (tools: ArrayList<Tool>) -> Unit)
}