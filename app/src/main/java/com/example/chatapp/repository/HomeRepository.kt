package com.example.chatapp.repository

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeRepository {
    private val database = Firebase.database
    private val myRef = database.getReference("message")

    val s = myRef.setValue("Hello, World!")
}