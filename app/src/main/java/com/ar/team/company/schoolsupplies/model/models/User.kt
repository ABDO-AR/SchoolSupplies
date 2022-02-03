package com.ar.team.company.schoolsupplies.model.models

// DataClass(User):
data class User(
    // Constructor:
    val id: String, var userName: String, var schoolName: String,
    var currentYear: String, var email: String, var password: String,
    var phoneNumber: String, var address: String, var userImage: String = NO_USER_IMAGE
) {
    // Companion:
    companion object {
        // Fields:
        const val NO_USER_IMAGE: String = "NO_USER_IMAGE_SET"
    }
}