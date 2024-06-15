package com.example.chatapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account
import com.example.chatapp.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

@SuppressLint("StaticFieldLeak")

class MessageViewModel(
    val context: Context
) : ViewModel(){
    private val _messageList = mutableStateOf<List<Message>>(emptyList())

    val messageList: MutableState<List<Message>> = _messageList
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().reference

    private val dataAccount: DatabaseReference = database.child("accounts")

    private val _user = mutableStateOf<Account?>(null)

    val user: State<Account?> = _user

    fun getFriend(uid: String) {
        dataAccount.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<Account>()
                for (accountSnapshot in snapshot.children) {
                    val account = accountSnapshot.getValue(Account::class.java)
                    account?.let {
                        if (it.uid == uid) {
                            userList.add(it)
                        }
                    }
                }
                _user.value = userList.firstOrNull()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun fetchMessage(currentUserUid: String, friendUid: String) {
        database.child("messages").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let {
                        if ((it.idFrom == currentUserUid && it.idTo == friendUid) ||
                            (it.idFrom == friendUid && it.idTo == currentUserUid)
                        ) {
                            messages.add(it)
                            Log.d("fetchMes",it.message)
                        }
                    }
                }
                _messageList.value = messages
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun splitStringIntoSegments(text: String, segmentLength: Int): List<String> {
        val segments = mutableListOf<String>()
        var currentPosition = 0
        val textLength = text.length

        while (currentPosition < textLength) {
            var endIndex = currentPosition + segmentLength
            if (endIndex > textLength) {
                endIndex = textLength
            }
            val segment = text.substring(currentPosition, endIndex)

            segments.add(segment.trim())
            currentPosition = endIndex
        }

        return segments
    }

    fun sendMessage(messageText: String,currentUid:String, friendUid: String, type: Int) {
        val messageId = database.child("messages").push().key ?: return

        val lines = messageText.split("\n")

        val processedLines = mutableListOf<String>()

        for (line in lines) {
            val lineSegments = splitStringIntoSegments(line, 40)

            val processedLine = lineSegments.joinToString("\n")
            processedLines.add(processedLine)
        }

        val processedMessageText = processedLines.joinToString("\n")

    Log.d("FIXTEXT", processedMessageText)
        val message = Message(
            id = messageId,
            idFrom = currentUid,
            idTo = friendUid,
            message = processedMessageText,
            type = type,
            timestamp = System.currentTimeMillis()
        )
        database.child("messages").child(messageId).setValue(message)
    }
    fun sendImageMessage(messageText: String,currentUid:String,
                         friendUid: String, type: Int) {
        val messageId = database.child("messages").push().key ?: return
        val message = Message(
            id = messageId,
            idFrom = currentUid,
            idTo = friendUid,
            message = messageText,
            type = type,
            timestamp = System.currentTimeMillis()
        )
        database.child("messages").child(messageId).setValue(message)
    }

    fun uploadImageMessage(context: Context, fileUri: Uri, onSuccess: (String) -> Unit) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val fileName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("messages/$fileName")

        val uploadTask = imageRef.putFile(fileUri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                onSuccess(downloadUrl)
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteMessageFromFirebase(messageId: String) {
        database.child("messages").child(messageId)
            .removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Data is deleted.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "delete fail.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}