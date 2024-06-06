package com.example.chatapp.viewmodel

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
                        if (it.uid != currentUserUid) {
                            accounts.add(it)
                        }
                    }
                }
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
                val chatItems = accounts.map { account ->
                    val key = if(account.uid < currentUserUid){
                        "${account.uid}_${currentUserUid}"
                    }else{
                        "${currentUserUid}_${account.uid}"
                    }
                    val (lastMessage, idFrom) = lastMessagesMap[key] ?: return
                        accountToChatItem(account, lastMessage,idFrom==currentUserUid)
                }.sortedByDescending { it.timestamp }

                _chatItemList.value = chatItems
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun accountToChatItem(account: Account, lastMessage: Message?, isMy: Boolean)
    : ChatItem {
        val id = account.uid
        val nickname = account.nickName
        val imageUri = account.imageUri
        val isOnline = account.status == "online"
        val timestamp = account.timestamp

        val lastMessageContent =
            if(isMy){
                when (lastMessage?.type) {
                    1 -> "You: Picture"
                    else -> "You: ${lastMessage?.message}"
                }
            }else{
                when (lastMessage?.type) {
                    1 -> "Picture"
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
            isOnline = isOnline
        )
    }
}





