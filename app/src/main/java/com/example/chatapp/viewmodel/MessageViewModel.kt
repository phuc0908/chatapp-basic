package com.example.chatapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.R
import com.example.chatapp.model.ChatItem
import com.example.chatapp.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageViewModel(

) : ViewModel(){
    private val _messageList = mutableStateOf<List<Message>>(emptyList())

    val messageList: MutableState<List<Message>> = _messageList
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().reference

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
                        }
                    }
                }
                _messageList.value = messages
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun sendMessage(messageText: String,currentUid:String, friendUid: String, type: Int) {
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
}