package com.example.chatapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.chatapp.screens.Main
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance().currentUser?.uid

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    if(auth!=null){
        startService(Intent(this, StatusService::class.java))
        getFCMTokenAndUpdate()
    }
        setContent {
            Main()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if(auth!=null){
            stopService(Intent(this, StatusService::class.java))
        }
    }

    private fun getFCMTokenAndUpdate() {
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
