package com.example.chatapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatapp.model.Call
import com.example.chatapp.R

@Composable
fun RowACall(
    call: Call
) {
    Row (
        Modifier
            .fillMaxWidth()
            .clickable {

            }
            .padding(5.dp)
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
//        left row
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            AvatarIcon(
                imageUrl = call.avatar,
                modifier = Modifier
                    .size(50.dp)
                    .aspectRatio(1f),
                onClick = { },
                isOnline = false
            )

            Column(
                Modifier
                    .height(45.dp),
            ){
                TextNameUser(name = call.name)
                TextChat(text = "Today, 9:30 AM")
            }
        }
//        right row
        Row{
            RoundIconButton(
                imageResId = R.drawable.call,
                imageVector = null,
                modifier = Modifier.size(40.dp)
            ) {

            }
            RoundIconButton(
                imageResId = R.drawable.camera,
                imageVector = null,
                modifier = Modifier.size(40.dp)
            ) {

            }
        }
    }
}
