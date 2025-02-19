package com.example.chatapp.model


data class ChatItem(
    val id: String,
    val avatar: String,
    val name: String,
    val lastMessage: String = "",
    val timestamp: Long?,
    val isFriend: Boolean? = false,
    val isOnline: Boolean? = false,
    val activeStatus: String? = null
)

data class Account(
    val uid: String = "",
    val fcmToken : String = "",
    val username: String = "",
    val password: String = "123456",
    val nickName: String = "user_",
    val imageUri: String = "https://firebasestorage.googleapis.com/v0/b/chatapp-4e975.appspot.com/o/avatars%2FnewUser.png?alt=media&token=428ada2b-c505-4af7-9da7-370ea0086e56",
    val status: String = "offline",
    val timestamp: Long? = 0L,
    val activeStatus: String? = null
){
    constructor() :this("","","","","","","",0L,null)
}




