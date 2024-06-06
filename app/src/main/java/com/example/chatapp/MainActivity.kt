package com.example.chatapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.chatapp.screens.Main
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    private val auth = FirebaseAuth.getInstance().currentUser?.uid

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    if(auth!=null){
        startService(Intent(this, StatusService::class.java))
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
}
