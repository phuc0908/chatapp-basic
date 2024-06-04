package com.example.chatapp.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.Account
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
@SuppressLint("StaticFieldLeak")

class AccountViewModel:ViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("accounts")
//    FUNCTION
//Realtime Firebase

    fun updateAccount(account: Account,user: FirebaseUser){
        user.run {
            val userIdReference = reference.child(user.uid)
            userIdReference.setValue(account)
        }
    }
    private fun updateAvatar(imageUrl: String,user: FirebaseUser){
        user.run {
            val reference = reference.child(user.uid).child("imageUri")
            Log.d("ddddddd",imageUrl)
            reference.setValue(imageUrl)
        }
    }
    fun setCurrentAccount(
        user: FirebaseUser,
        context: Context,
        onResult: (Account?) -> Unit)
    {
        reference.child(user.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val account = snapshot.getValue(Account::class.java)
                onResult(account)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,
                    "Failed to load data: ${error.message}",
                    Toast.LENGTH_SHORT).show()
                onResult(null)
            }
        })
    }
    private fun updateAccountByUid(uid: String, onDataChange: (Account?) -> Unit, onCancelled: ((error: DatabaseError) -> Unit)? = null) {
        val ref = reference.child(uid)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val account = snapshot.getValue(Account::class.java)
                onDataChange(account)
            }
            override fun onCancelled(error: DatabaseError) {
                onCancelled?.invoke(error)
            }
        })
    }

    fun uploadImageToFirebase(context: Context, fileUri: Uri, onSuccess: (String) -> Unit) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val fileName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("avatars/$fileName")

        val uploadTask = imageRef.putFile(fileUri)

        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                onSuccess(downloadUrl)
            }
        }.addOnFailureListener {
            Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
        }
    }


    fun updateUserImageUri(imageUrl: String,user: FirebaseUser) {
        updateAccountByUid(
            user.uid,
            onDataChange = { account ->
                if (account != null) {
                    updateAvatar(imageUrl,user)
                } else {
                    Log.d("","updateUserImageUri : account null")
                }
            }, onCancelled = { error ->
                Log.d("","updateUserImageUri : onCancelled")
                Log.d("",error.message)
            }
        )
    }

    fun updateUserNickName(name: String, user: FirebaseUser ) {
        updateAccountByUid(
            user.uid,
            onDataChange = { account ->
                if (account != null) {
                    val reference = reference
                                    .child(user.uid)
                                    .child("nickName")
                    reference.setValue(name)
                } else {
                    Log.d("","updateNickName : account null")
                }
            }, onCancelled = { error ->
                Log.d("","updateNickName : onCancelled")
                Log.d("",error.message)
            }
        )
    }

}