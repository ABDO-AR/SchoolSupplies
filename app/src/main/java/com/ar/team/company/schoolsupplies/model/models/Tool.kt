package com.ar.team.company.schoolsupplies.model.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

@Suppress("unused")
data class Tool(
    var toolID:String, var toolBasicID:String, val name: String, val details: String,
    val type: String, var toolImage: String, val ownerID: String, var toolRequestID: String, var toolRejectedIDs: String,
    var toolAcceptedIDs: String) {

    // Constructor:
    constructor() : this("","","", "", "", "", "","","","")

    // Method(Decode):
    @Deprecated("This making loading images are too slow operation")
    fun decode(): Bitmap {
        // Initializing:
        val bytes: ByteArray = Base64.decode(toolImage, Base64.DEFAULT)
        // Returning:
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
