package com.example.chatapp_dacs3.ui.screens.message

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatapp_dacs3.R
import com.example.chatapp_dacs3.model.Message
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessageViewModel : ViewModel(){
    var message by mutableStateOf<List<Message>?>(null)
        private set
    var database = Firebase.database
        private set
    var myRef = database.getReference("message")
        private set

    fun fetchMessage() {
        val list = listOf(
            Message(1,2, type = 0, message = "Hello"),
            Message(1,2, type = 0, message = "My name is Phuc"),
            Message(2,1, type = 0, message = "Right, can I trust you"),
            Message(1,2, type = 0, message = "Yes , you see, this is mine"),
            Message(1,2, type = 1, image = R.drawable.avatar_garena_2),
            Message(2,1, type = 1, image = R.drawable.avatar_girl_garena),

            )
        message = list
    }


    fun insertMes(message: Message){
        myRef.setValue("Hello, World!")
    }
}