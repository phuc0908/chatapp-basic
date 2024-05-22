package com.example.chatapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account
import com.example.chatapp.model.ChatItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeViewModel : ViewModel(

) {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("accounts")

    private val _chatItemList = mutableStateOf<List<ChatItem>>(emptyList())

    val chatItemList: MutableState<List<ChatItem>> = _chatItemList

    fun fetchAccountList(currentUserUid: String) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val chatItems = mutableListOf<ChatItem>()
                for (accountSnapshot in snapshot.children) {
                    val account = accountSnapshot.getValue(Account::class.java)
                    account?.let {
                        if (it.uid != currentUserUid) {
                            chatItems.add(accountToChatItem(it))
                        }
                    }
                }
                _chatItemList.value = chatItems
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun accountToChatItem(account: Account): ChatItem {
        val id = account.uid
        val nickname = account.nickName
        val imageUri = account.imageUri

        return ChatItem(
            id = id,
            name = nickname,
            avatar = imageUri,
            lastMessage = "Don't have message",
            timeAgo = 0,
            isFriend = false,
            isOnline = false
        )
    }
}





