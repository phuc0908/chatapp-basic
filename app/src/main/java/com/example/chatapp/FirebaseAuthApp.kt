package com.example.chatapp

import android.app.Application
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseAuthApp : Application(){
    val auth = Firebase.auth
}