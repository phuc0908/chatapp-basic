package com.example.chatapp.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class AccountViewModel:ViewModel() {

    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("accounts")
    private val user = FirebaseAuth.getInstance().currentUser
    val account: Account? = null

    private val _nickName = mutableStateOf("")
    val nickName: String
        get() = _nickName.value

//    FUNCTION
    fun updateNickName(newName: String) {
        _nickName.value = newName
    }

    fun updateAccount(account: Account){
        user?.run {
            val userIdReference = reference.child(user.uid)

            userIdReference.setValue(account)
        }
    }
    private fun updateAvatar(imageUrl: String){
        user?.run {
            val reference = reference.child(user.uid).child("imageUri")

            reference.setValue(imageUrl)
        }
    }


    fun setCurrentAccount(context: Context, onResult: (Account?) -> Unit){

        if (user != null) {
            reference.child(user.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val account = snapshot.getValue(Account::class.java)
                    onResult(account)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Failed to load data: ${error.message}", Toast.LENGTH_SHORT).show()
                    onResult(null)
                }
            })
        }else{
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
            onResult(null)
        }
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


    fun updateUserImageUri(imageUrl: String) {

        user?.let {
            getAccountByUid(it.uid,
                onDataChange = { account ->
                    if (account != null) {
                        updateAvatar(imageUrl)
                    } else {
                        Log.d("","updateUserImageUri : account null")
                    }
                }, onCancelled = { error ->
                    Log.d("","updateUserImageUri : onCancelled")
                    Log.d("",error.message)
                }
            )
        }
    }

    fun updateUserNickName(name: String) {

        user?.let {
            getAccountByUid(it.uid,
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

    private fun getAccountByUid(uid: String, onDataChange: (Account?) -> Unit, onCancelled: ((error: DatabaseError) -> Unit)? = null) {
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("accounts").child(uid)

        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val account = snapshot.getValue(Account::class.java)
                onDataChange(account)
            }

            override fun onCancelled(error: DatabaseError) {
                onCancelled?.invoke(error)
            }
        })
    }

}