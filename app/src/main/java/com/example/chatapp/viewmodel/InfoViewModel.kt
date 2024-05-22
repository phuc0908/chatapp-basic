package com.example.chatapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.chatapp.model.Account

class InfoViewModel: ViewModel() {
    val account : Account = Account()
}