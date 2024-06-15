package com.example.chatapp.model.NOT_USE

data class Team (
    val id: String = "",
    val name: String = "",
    val picture: String = "",
    val idHost: String = "",
    val sizeAccount: Int = 0,

)

data class TeamAccount (
    val id: String = "",
    val idTeam: String = "",
    val idAccount: String = "",
)
