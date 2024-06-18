package com.example.chatapp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class NotificationViewModel :ViewModel(){

    fun getFCMTokenAndUpdate() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                if (token != null) {
                    updateFCMTokenInDatabase(token)
                }
            } else {
                task.exception?.printStackTrace()
            }
        }
    }
    private fun updateFCMTokenInDatabase(token: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            val databaseReference = FirebaseDatabase.getInstance()
                .getReference("accounts").child(userId)
            databaseReference.child("fcmToken").setValue(token)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Update FCM token", token)
                    } else {
                        task.exception?.printStackTrace()
                    }
                }
        }
    }
}