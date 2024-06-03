package com.example.chatapp.viewmodel

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ImageRequest
import com.example.chatapp.R
import com.example.chatapp.model.Account
import com.example.chatapp.model.ChatItem
import com.example.chatapp.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.UUID

@SuppressLint("StaticFieldLeak")

class MessageViewModel(
    val context: Context
) : ViewModel(){
    private val _messageList = mutableStateOf<List<Message>>(emptyList())

    val messageList: MutableState<List<Message>> = _messageList
    private val database: DatabaseReference =
        FirebaseDatabase.getInstance().reference

    private val dataAccount: DatabaseReference = database.child("accounts")

    private val _user = mutableStateOf<Account?>(null)

    val user: State<Account?> = _user

    fun getFriend(uid: String) {
        dataAccount.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<Account>()
                for (accountSnapshot in snapshot.children) {
                    val account = accountSnapshot.getValue(Account::class.java)
                    account?.let {
                        if (it.uid == uid) {
                            userList.add(it)
                        }
                    }
                }
                _user.value = userList.firstOrNull()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun fetchMessage(currentUserUid: String, friendUid: String) {
        database.child("messages").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val messages = mutableListOf<Message>()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let {
                        if ((it.idFrom == currentUserUid && it.idTo == friendUid) ||
                            (it.idFrom == friendUid && it.idTo == currentUserUid)
                        ) {
                            messages.add(it)
                            Log.d("fetchMes",it.message)
                        }
                    }
                }
                _messageList.value = messages
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun sendMessage(messageText: String,currentUid:String, friendUid: String, type: Int) {
        val messageId = database.child("messages").push().key ?: return
        val message = Message(
            id = messageId,
            idFrom = currentUid,
            idTo = friendUid,
            message = messageText,
            type = type,
            timestamp = System.currentTimeMillis()
        )
        database.child("messages").child(messageId).setValue(message)
    }
    fun sendImageMessage(messageText: String,currentUid:String,
                         friendUid: String, type: Int) {
        val messageId = database.child("messages").push().key ?: return
        val message = Message(
            id = messageId,
            idFrom = currentUid,
            idTo = friendUid,
            message = messageText,
            type = type,
            timestamp = System.currentTimeMillis()
        )
        database.child("messages").child(messageId).setValue(message)
    }

    fun uploadImageMessage(context: Context, fileUri: Uri, onSuccess: (String) -> Unit) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val fileName = UUID.randomUUID().toString()
        val imageRef = storageRef.child("messages/$fileName")

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


    fun deleteMessageFromFirebase(messageId: String) {
        database.child("messages").child(messageId)
            .removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Data is deleted.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "delete fail.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    fun generateRandomFileName(): String {
        val currentDate = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val randomSuffix = (1000..9999).random()
        return "${currentDate}_$randomSuffix.png"
    }


    suspend fun downloadAndSaveImageToGallery(imageUrl: String) {
        withContext(Dispatchers.IO) {
            try {
                val url = URL(imageUrl)
                val bitmap: Bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                saveImageToGallery(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun saveImageToGallery( bitmap: Bitmap) {
        val fileName = generateRandomFileName()
        val imagesDirectory = File(Environment.
        getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "images")
        if (!imagesDirectory.exists()) {
            imagesDirectory.mkdirs()
        }
        val imageFile = File(imagesDirectory, fileName)

        val outputStream: OutputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        val contentResolver: ContentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES + File.separator + "images")
        }
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

}