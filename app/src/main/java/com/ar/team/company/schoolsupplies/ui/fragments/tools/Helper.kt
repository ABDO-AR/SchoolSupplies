package com.ar.team.company.schoolsupplies.ui.fragments.tools

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

object Helper {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val mainRef: DatabaseReference = database.getReference()

    val storage: FirebaseStorage = FirebaseStorage.getInstance()
    val usersRef: DatabaseReference = mainRef.child("Users")

}