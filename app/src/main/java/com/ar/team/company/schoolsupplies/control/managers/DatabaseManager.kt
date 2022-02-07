package com.ar.team.company.schoolsupplies.control.managers

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

@Suppress("MemberVisibilityCanBePrivate")
object DatabaseManager {

    // Constance:
    const val USERS_DB_REFERENCE: String = "Users"
    const val TOOLS_DB_REFERENCE: String = "Tools"
    const val ORDERSS_DB_REFERENCE: String = "Orders"

    // Database:
    val db: FirebaseDatabase = FirebaseDatabase.getInstance()
    val dbReference: DatabaseReference = db.reference
    val usersDBReference: DatabaseReference = dbReference.child(USERS_DB_REFERENCE)
    val toolsDBReference: DatabaseReference = dbReference.child(TOOLS_DB_REFERENCE)
    val ordersDBReference: DatabaseReference = dbReference.child(ORDERSS_DB_REFERENCE)

    // Storage:
    val storage: FirebaseStorage = FirebaseStorage.getInstance()
}