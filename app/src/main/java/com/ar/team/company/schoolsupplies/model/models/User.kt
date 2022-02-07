@file:Suppress("unused")

package com.ar.team.company.schoolsupplies.model.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

// DataClass(User):
data class User(
    // Constructor:
    val id: String, var userName: String, var schoolName: String,
    var currentYear: String, var email: String, var password: String,
    var phoneNumber: String, var address: String, var userImage: String = NO_USER_IMAGE
) {

    // Constructor:
    constructor() : this("", "", "", "", "", "", "", "", "")

    // Companion:
    companion object {
        // Fields:
        const val NO_USER_IMAGE: String = "NO_USER_IMAGE_SET"
    }

    // Method(Decode):
    fun decode(): Bitmap {
        // Initializing:
        val bytes: ByteArray = Base64.decode(userImage, Base64.DEFAULT)
        // Returning:
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}