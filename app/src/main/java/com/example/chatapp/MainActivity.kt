package com.example.chatapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.chatapp.model.Message
import com.example.chatapp.screens.Main
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    private val databaseReference = FirebaseDatabase.getInstance().getReference("messages")

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermission()
        getFCMTokenAndUpdate()
        createNotificationChannel()
        getNotification()


        if(currentUserId!=null){
            startService(Intent(this, StatusService::class.java))
        }
        setContent {
            Main()
        }
    }
    private fun getNotification(){
        val myMessagesQuery = databaseReference.orderByChild("idTo").equalTo(currentUserId)
        myMessagesQuery.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)!!
                if (message.idTo == currentUserId) {
                    createNotificationChannel()
                    showNotification("New Message", message.message)
                }else {
                    Log.d("Notification", "Message from current user or message is null")
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }
            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                "android.permission.POST_NOTIFICATIONS"
            ) == PackageManager.PERMISSION_GRANTED
            if(!hasPermission){
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf("android.permission.POST_NOTIFICATIONS"),
                    0
                )
            }
        }else{
            Log.d("requestNotificationPermission","_______________________________")
        }
    }

    val CHANNEL_ID = "my_app_notification_channel"
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notification = builder.build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(123 , notification)
    }


    override fun onDestroy() {
        super.onDestroy()
        if(currentUserId!=null){
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
