package com.example.chatapp.ui.screens.signIn

data class SignInResult(
    val data: UserData?,
    val errorMes: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?
)
