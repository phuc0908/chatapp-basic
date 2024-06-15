package com.example.chatapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account
import com.example.chatapp.model.ChatItem
import com.example.chatapp.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel()
{
    private val accountsDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("accounts")
    private val messagesDatabase: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("messages")

    private val _chatItemList = mutableStateOf<List<ChatItem>>(emptyList())
    val chatItemList: MutableState<List<ChatItem>> = _chatItemList

    fun fetchAccountListWithLastMessages(currentUserUid: String)
    {
        accountsDatabase.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(accountSnapshot: DataSnapshot) {
                val accounts = mutableListOf<Account>()
                for (accountSnap in accountSnapshot.children) {
                    val account = accountSnap.getValue(Account::class.java)
                    account?.let {
                        accounts.add(it)
                        Log.d("Uid:",account.uid)
                    }
                }
                Log.d("AccountSize1",accounts.size.toString())
                fetchLastMessagesForAccounts(currentUserUid, accounts)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun fetchLastMessagesForAccounts(currentUserUid: String, accounts: List<Account>)
    {
        messagesDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(messageSnapshot: DataSnapshot) {
                val lastMessagesMap = mutableMapOf<String, Pair<Message, String>>()

                for (messageSnap in messageSnapshot.children) {
                    val message = messageSnap.getValue(Message::class.java)
                    message?.let {
                        val key = if (it.idFrom < it.idTo) {
                            "${it.idFrom}_${it.idTo}"
                        } else {
                            "${it.idTo}_${it.idFrom}"
                        }
                        if (!lastMessagesMap.containsKey(key) ||
                            (lastMessagesMap[key]?.first?.timestamp ?: 0) < it.timestamp
                        ) {
                            lastMessagesMap[key] = Pair(it, it.idFrom)
                        }
                    }
                }
                Log.d("AccountSize2",accounts.size.toString())
                Log.d("AccountSize2",accounts.size.toString())
                val chatItems = mutableListOf<ChatItem>()
                for (account in accounts) {
                    val key = if (account.uid < currentUserUid) {
                        "${account.uid}_${currentUserUid}"
                    } else {
                        "${currentUserUid}_${account.uid}"
                    }
                    val (lastMessage, idFrom) = lastMessagesMap[key] ?: continue
                    chatItems.add(accountToChatItem(account, lastMessage, idFrom == currentUserUid))
                }
                _chatItemList.value = chatItems.sortedByDescending { it.timestamp }

                Log.d("ChatItemsSize",_chatItemList.value.size.toString())
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("onCancelled",error.message)
            }
        })
    }
    private fun accountToChatItem(account: Account, lastMessage: Message?, isMy: Boolean)
    : ChatItem {
        val id = account.uid
        val nickname = account.nickName
        val imageUri = account.imageUri
        val isOnline = account.status == "online"

        val lastMessageContent =
            if(isMy){
                when (lastMessage?.type) {
                    1 -> "You: Picture"
                    2 -> "You: Video"
                    else -> "You: ${lastMessage?.message}"
                }
            }else{
                when (lastMessage?.type) {
                    1 -> "Picture"
                    2 -> "Video"
                    else -> lastMessage?.message ?: ""
                }
            }
        return ChatItem(
            id = id,
            name = nickname,
            avatar = imageUri,
            lastMessage = lastMessageContent,
            timestamp =  lastMessage?.timestamp,
            isFriend = false,
            isOnline = isOnline,
            activeStatus = account.activeStatus
        )
    }
    fun deleteAllMessagesWithAFriend(context : Context, currentUserUid: String, friendUid: String) {
        messagesDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    message?.let {
                        if ((it.idFrom == currentUserUid && it.idTo == friendUid) ||
                            (it.idFrom == friendUid && it.idTo == currentUserUid)
                        ) {
                            messageSnapshot.ref.removeValue()
                        }
                    }
                }
                Toast.makeText(
                    context,
                    "Deleted Conversation.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}





