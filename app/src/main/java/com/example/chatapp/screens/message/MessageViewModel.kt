package com.example.chatapp.screens.message

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatapp.R
import com.example.chatapp.model.Message

class MessageViewModel : ViewModel(){
    var message by mutableStateOf<List<Message>?>(null)
        private set

    fun fetchMessage() {
        val list = listOf(
            Message(1,2, type = 0, message = "Hello"),
            Message(1,2, type = 0, message = "My name is Phuc"),
            Message(2,1, type = 0, message = "Right, can I trust you"),
            Message(1,2, type = 0, message = "Yes , you see, this is mine"),
            Message(1,2, type = 1, image = R.drawable.avatar_garena_2),
            )
        message = list
    }


}