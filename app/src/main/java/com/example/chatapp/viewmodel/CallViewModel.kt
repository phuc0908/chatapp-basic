package com.example.chatapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Call
import com.example.chatapp.R

class CallViewModel: ViewModel() {
    var calls by mutableStateOf<List<Call>?>(null)
        private set
    fun fetchMyCall() {
        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/chatapp-4e975.appspot.com/o/avatars%2FnewUser.png?alt=media&token=428ada2b-c505-4af7-9da7-370ea0086e56"
        val list = listOf(
            Call(imageUrl, "Phuc Ís meeeee"),
            Call(imageUrl, "?????????"),
        )
        calls = list
    }
}