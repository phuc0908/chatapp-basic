package com.example.chatapp.model

data class Message(
    val id: String = "",
    val idFrom: String = "",
    val idTo: String = "",
    val message: String = "",
    val type: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)