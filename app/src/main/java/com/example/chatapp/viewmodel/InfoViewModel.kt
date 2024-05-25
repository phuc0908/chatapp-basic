package com.example.chatapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InfoViewModel (): ViewModel(){
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val dataAccount: DatabaseReference = database.child("accounts")

    private val _user = mutableStateOf<Account?>(null)
    val user: State<Account?> = _user

    fun getFriend(uid: String) {
        Log.d("InfoViewModel", "getFriend called with uid: $uid")
        dataAccount.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("InfoViewModel", "onDataChange called")
                val userList = mutableListOf<Account>()
                for (accountSnapshot in snapshot.children) {
                    val account = accountSnapshot.getValue(Account::class.java)
                    Log.d("InfoViewModel", "Account snapshot: $account")
                    account?.let {
                        if (it.uid == uid) {
                            userList.add(it)
                        }
                    }
                }
                _user.value  = userList.firstOrNull()
                if (_user.value == null) {
                    Log.d("getFriend", "User với UID $uid không được tìm thấy.")
                } else {
                    Log.d("getFriend", "User với UID $uid được tìm thấy: ${_user.value}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("getFriend", "Database error: ${error.message}")
            }
        })
    }
}