package com.example.chatapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun EmojiPicker(onEmojiSelected: (String) -> Unit) {
    val emojis = listOf("😊", "😂", "😍", "🥺", "😎", "🤔", "👍", "🙏")
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
    ) {
        emojis.forEach { emoji ->
            Text(
                text = emoji,
                modifier = Modifier
                    .clickable { onEmojiSelected(emoji) }
                    .padding(4.dp)
            )
        }
    }
}