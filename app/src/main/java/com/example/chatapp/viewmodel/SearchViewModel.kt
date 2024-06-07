package com.example.chatapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class SearchViewModel: ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val dataAccount: DatabaseReference = database.child("accounts")

    private val curentId = FirebaseAuth.getInstance().currentUser?.uid

    private val _users = mutableStateOf<List<Account>>(emptyList())
    val users: State<List<Account>> = _users

    private val _allAccount = mutableStateOf<List<Account>>(emptyList())
    val allAccount: State<List<Account>> = _allAccount

    fun searchByName(name: String) {

        if (name.isEmpty()) {
            _users.value = emptyList()
            return
        }
        dataAccount.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("SearchViewModel", "onDataChange called")

                val userList = mutableListOf<Account>()
                val query = name.trim().lowercase()

                for (accountSnapshot in snapshot.children) {
                    val account = accountSnapshot.getValue(Account::class.java)
                    Log.d("SearchViewModel", "Account snapshot: $account")

                    account?.let {
                        if (it.nickName.lowercase(Locale.getDefault()).contains(query)) {
                            userList.add(it)
                            Log.d("NameSearch", it.nickName)
                        }
                    }
                }
                _users.value  = userList
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("getFriend", "Database error: ${error.message}")
            }
        })
    }

    fun getAllAccount() {
        dataAccount.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<Account>()

                for (accountSnapshot in snapshot.children) {
                    val account = accountSnapshot.getValue(Account::class.java)
                    account?.let {
                        userList.add(it)
                        Log.d("Account",account.uid)
                    }
                }
                _allAccount.value  = userList
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}