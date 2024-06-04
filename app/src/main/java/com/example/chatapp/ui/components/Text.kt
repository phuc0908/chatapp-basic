package com.example.chatapp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextNameUser(
    name: String
) {
    Text(text = name,
        style = TextStyle(fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        ),
        maxLines = 1,
    )
}

@Composable
fun TextChat(
    text: String
) {
    Text(text = text,
        style = TextStyle(
            fontSize = 14.sp,
            color = Color.Gray
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}