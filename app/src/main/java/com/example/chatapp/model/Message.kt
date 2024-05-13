package com.example.chatapp.model

data class Message(val id: Int?,
                   val idFrom: Int,
                   val idTo: Int,
                   val type: Int,
                   val message: String?,
                   val image: Int?
){
    constructor(idFrom: Int,idTo: Int, type: Int, message: String) :
            this(0, idFrom, idTo, type, message, 0)
    constructor(idFrom: Int,idTo: Int, type: Int, image: Int) :
            this(0, idFrom, idTo, type, "", image)
}