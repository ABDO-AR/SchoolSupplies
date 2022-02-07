package com.ar.team.company.schoolsupplies.model.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

@Suppress("unused")
data class Message(
    var id:String, val message: String, val userSenderID: String,
    val userReceiverID: String, val userSenderName: String,val userReceiverName: String, val userSenderImage: String) {

    // Constructor:
    constructor() : this("","", "",  "","","","")

    // Method(Decode):
    fun decode(): Bitmap {
        // Initializing:
        val bytes: ByteArray = Base64.decode(userSenderImage, Base64.DEFAULT)
        // Returning:
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
