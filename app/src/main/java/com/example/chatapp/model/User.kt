package com.example.chatapp.model

data class User(val avatar: Int,
                val name: String,
                val lastMessage: String?,
                val timeAgo: Int?,
                val isFriend: Boolean?=false,
                val isOnline: Boolean?=false,
    ) {
    constructor(avatar: Int, name: String) : this(avatar, name, null, null, false, false)
}

data class Account(val uid: String,
                   val username: String,
                   val password: String,
                   val nickName: String,
                   val imageUri: String
){
    constructor(): this("","","","","")
}



