package com.ar.team.company.schoolsupplies.model.intentions

sealed class SignIntentions {
    // Intention(SignIn):
    data class SignIn(val email: String, val password: String) : SignIntentions()

    // Intention(SignUp):
    data class SignUp(val email: String, val password: String, val currentYear: String, val userName: String, val schoolName: String, val phoneNumber: String, val address: String) : SignIntentions()
}
