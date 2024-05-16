package com.example.chatapp.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatapp.R
import com.example.chatapp.model.User


class HomeViewModel : ViewModel() {
    var statusFriend by mutableStateOf<List<User>?>(null)
        private set
    var lastFriend by mutableStateOf<List<User>?>(null)
        private set
    fun fetchStatusFriend() {
        val friends = listOf(
            User(R.drawable.newuser, "Nguyen Phuc",),
            User(R.drawable.newuser, "Mai Thuong"),
            User(R.drawable.newuser, "Tran Dang"),
            User(R.drawable.newuser, "Wong Da"),
            User(R.drawable.newuser, "Ton Lu"),
            User(R.drawable.newuser, "Tao La Ai"),
            User(R.drawable.newuser, "Phuc Is Me"),
            User(R.drawable.newuser, "EEEEEE"),
            User(R.drawable.newuser, "DDDDg")
        )
        statusFriend = friends
    }


    fun fetchLastFriend() {
        val friends = listOf(
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),
            User(R.drawable.newuser, "Ton Lu","Im a dog",0),
            User(R.drawable.avatar_garena_2, "TDDDD",":)))))))))",32),
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),
            User(R.drawable.newuser, "Nguyen Phuc","Hello you are my teacher",34),


        )
        lastFriend = friends
    }
}





