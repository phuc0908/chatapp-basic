package com.example.chatapp_dacs3.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.chatapp_dacs3.ui.theme.LightGreen1

@Composable
fun Message(
    message: String ,
    isMyMessage: Boolean
) {
    val messageLength = message.length
    Log.d("Message.Length", messageLength.toString())
    val boxHeight = if (messageLength <= 50) 50.dp else 65.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()

            .padding(
                start = if (isMyMessage)50.dp else 7.dp,
                end =   if (isMyMessage)7.dp else 50.dp,
                top = 5.dp,
                bottom = 5.dp
            ),
        horizontalAlignment =
                if (isMyMessage)
                    Alignment.End
                else
                    Alignment.Start,
    ) {
        // Display message
        val topStartRadius = if (isMyMessage) 8.dp else 0.dp
        val topEndRadius = if (!isMyMessage) 8.dp else 0.dp
        Box(
            modifier = Modifier
                .height(boxHeight)
                .clip(
                    RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp,
                        topStart = topStartRadius,
                        topEnd = topEndRadius
                    )
                )
                .background(
                    if (isMyMessage)
                    LightGreen1
                    else
                    Color.LightGray
                )
                .padding(15.dp),
            contentAlignment =
                    if (isMyMessage)
                        Alignment.CenterEnd
                    else
                        Alignment.CenterStart
        ) {
            Text(
                text = message,
                modifier = Modifier
                    .align(
                        if (isMyMessage)
                            Alignment.CenterEnd
                        else
                            Alignment.CenterStart
                    ),
                style = TextStyle(
                    color =
                        if (isMyMessage)
                            Color.White
                        else
                            Color.Black
                ))
        }
    }
}


