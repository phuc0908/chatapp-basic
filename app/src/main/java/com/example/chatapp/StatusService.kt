package com.example.chatapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UpdateStatusService : Service(), CoroutineScope {
    private var isServiceRunning = false
    private lateinit var job: Job

    private val database = FirebaseDatabase.getInstance().getReference("accounts")
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
        job = Job()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!isServiceRunning) {
            isServiceRunning = true
            startUpdatingStatus()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        isServiceRunning = false
        updateStatusInFirebase(false)
    }

    private fun startUpdatingStatus() {
        launch {
            while (isServiceRunning) {
                updateStatusInFirebase(true)
                delay(60_000)
                Log.d("UpdateStatus-Service","after 60s")
            }
        }
    }
    private fun updateStatusInFirebase(isConnected: Boolean) {
        val status = if (isConnected) "online" else "offline"
        val timestamp = System.currentTimeMillis()
        val userStatus = mapOf(
            "status" to status,
            "timestamp" to timestamp
        )

        if (userId != null) {
            database.child(userId).updateChildren(userStatus)
        }
    }
}






