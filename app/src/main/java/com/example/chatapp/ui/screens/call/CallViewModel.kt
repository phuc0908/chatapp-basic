package com.example.chatapp.ui.screens.call

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Call
import com.example.chatapp_dacs3.R

class CallViewModel: ViewModel() {
    var calls by mutableStateOf<List<Call>?>(null)
        private set
    fun fetchMyCall() {
        val list = listOf(
            Call(R.drawable.newuser, "Mai Thuong"),
            Call(R.drawable.avatar_garena_2, "Tran Dang"),
            Call(R.drawable.newuser, "Wong Da"),
            Call(R.drawable.newuser, "Ton Lu"),
            Call(R.drawable.avatar_garena_2, "Tao La Ai"),
            Call(R.drawable.newuser, "Rang"),
        )
        calls = list
    }
}