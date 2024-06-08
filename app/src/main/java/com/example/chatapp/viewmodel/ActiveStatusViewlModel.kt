package com.example.chatapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

enum class StatusOption {
    ON, OFF
}

class ActiveStatusViewModel : ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("accounts")

    private val _statusOption = mutableStateOf<StatusOption?>(null)
    val statusOption: State<StatusOption?> = _statusOption

    init {
        observeActiveStatus()
    }
    private fun observeActiveStatus() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { currentUser ->
            val reference = FirebaseDatabase.getInstance().reference.child(currentUser.uid).child("activeStatus")
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val status =
                        when (snapshot.getValue(String::class.java)) {
                        "ON" -> StatusOption.ON
                        "OFF" -> StatusOption.OFF
                        else -> null
                    }
                    _statusOption.value = status
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    fun updateActiveStatus(user: FirebaseUser, status: StatusOption) {
        user.run {
            val reference = reference.child(user.uid).child("activeStatus")
            reference.setValue(status.name)
            Log.d("updateActiveStatus",status.name)
            _statusOption.value = status
        }
    }
}
