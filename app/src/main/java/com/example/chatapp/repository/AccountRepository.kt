package com.example.chatapp.repository

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.chatapp.model.Account
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*

class AccountRepository {

    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("accounts")
    private val user = FirebaseAuth.getInstance().currentUser

    fun getCurrentAccount(context: Context, onResult: (Account?) -> Unit) {
        user?.let {
            reference.child(it.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val account = snapshot.getValue(Account::class.java)
                    onResult(account)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Failed to load data: ${error.message}", Toast.LENGTH_SHORT).show()
                    onResult(null)
                }
            })
        } ?: run {
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

    fun updateAvatar(imageUrl: String) {
        user?.let {
            reference.child(it.uid).child("imageUri").setValue(imageUrl)
        }
    }

    fun updateNickName(name: String) {
        user?.let {
            reference.child(it.uid).child("nickName").setValue(name)
        }
    }

    fun getAccountByUid(uid: String, onDataChange: (Account?) -> Unit, onCancelled: ((error: DatabaseError) -> Unit)? = null) {
        reference.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
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