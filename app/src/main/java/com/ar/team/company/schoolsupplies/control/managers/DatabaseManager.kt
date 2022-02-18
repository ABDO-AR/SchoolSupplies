package com.ar.team.company.schoolsupplies.control.managers

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

@Suppress("MemberVisibilityCanBePrivate", "unused")
object DatabaseManager {

    // Constance:
    const val USERS_DB_REFERENCE: String = "Users"
    const val TOOLS_DB_REFERENCE: String = "Tools"
    const val ORDERS_DB_REFERENCE: String = "Orders"
    const val MESSAGES_DB_REFERENCE: String = "Messages"

    // Constance(Storage):
    const val TOOLS_IMAGES_URL: String = "gs://schoolsupplies-b9361.appspot.com"
    const val TOOLS_IMAGES_FOLDER_NAME: String = "tools_images"


    // Database:
    val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    val dbReference: DatabaseReference = db.reference
    val usersDBReference: DatabaseReference = dbReference.child(USERS_DB_REFERENCE)
    val toolsDBReference: DatabaseReference = dbReference.child(TOOLS_DB_REFERENCE)
    val ordersDBReference: DatabaseReference = dbReference.child(ORDERS_DB_REFERENCE)
    val messagesDBReference: DatabaseReference = dbReference.child(MESSAGES_DB_REFERENCE)

    // Storage:
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
}