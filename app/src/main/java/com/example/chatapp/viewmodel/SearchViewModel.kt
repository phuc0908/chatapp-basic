package com.example.chatapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatapp.R
import com.example.chatapp.model.Account
import com.example.chatapp.model.YourRecentSearch
import com.example.chatapp.model.YourRecommendSearch
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SearchViewModel: ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val dataAccount: DatabaseReference = database.child("accounts")

    private val _user = mutableStateOf<List<Account>>(emptyList())
    val user: State<List<Account>> = _user
    fun searchRecommendByName(name: String) {
        Log.d("SearchViewModel", "getFriend called with uid: $name")

        dataAccount.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("SearchViewModel", "onDataChange called")

                val userList = mutableListOf<Account>()

                for (accountSnapshot in snapshot.children) {
                    val account = accountSnapshot.getValue(Account::class.java)
                    Log.d("SearchViewModel", "Account snapshot: $account")

                    account?.let {
                        if (it.uid == name) {
                            userList.add(it)
                        }
                    }
                }
                _user.value  = userList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("getFriend", "Database error: ${error.message}")
            }
        })
    }
}