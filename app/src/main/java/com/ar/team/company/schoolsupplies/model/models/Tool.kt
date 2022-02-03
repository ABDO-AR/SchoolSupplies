package com.ar.team.company.schoolsupplies.model.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

@Suppress("unused")
data class Tool(val name: String, val details: String, val type: String, val toolImage: String, val ownerID: String) {

    // Constructor:
    constructor() : this("", "", "", "", "")

    // Method(Decode):
    fun decode(): Bitmap {
        // Initializing:
        val bytes: ByteArray = Base64.decode(toolImage, Base64.DEFAULT)
        // Returning:
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}
