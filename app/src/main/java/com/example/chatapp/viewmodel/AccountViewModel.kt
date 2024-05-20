package com.example.chatapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AccountViewModel:ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("accounts")
    private val user = FirebaseAuth.getInstance().currentUser
    private val currentAccount = mutableStateOf(Account("","","","",""))


//    FUNCTION

    fun getAccount(){

    }
    fun createAccount(account: Account){
        user?.run {
            val userIdReference = reference.child(user.uid)

            userIdReference.setValue(account)
        }
    }

    fun setCurrentAccount(){

    }

}