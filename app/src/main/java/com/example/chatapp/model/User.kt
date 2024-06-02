package com.example.chatapp.model


data class ChatItem(
    val id: String,
    val avatar: String,
    val name: String,
    val lastMessage: String = "",
    val timestamp: Long?,
    val isFriend: Boolean? = false,
    val isOnline: Boolean? = false,
)

data class Account(val uid: String,
                   val username: String,
                   val password: String,
                   val nickName: String,
                   val imageUri: String
){
    constructor(): this("","","","","")
}




