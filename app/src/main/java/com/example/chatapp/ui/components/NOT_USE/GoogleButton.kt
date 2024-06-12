package com.example.chatapp.ui.components.NOT_USE

import android.service.autofill.OnClickAction
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.ui.theme.backgroundGoogle
import com.example.chatapp.R


@Composable
fun GoogleButton(
    clickAction: () -> Unit
) {
    Row (
        Modifier
            .clickable(onClick = clickAction)
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(3.dp))
            .padding(10.dp)
            .background(backgroundGoogle),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Image(
            painter = painterResource(id = R.drawable.google_logo),
            contentDescription = "",
            Modifier.padding(1.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Sign in with Google",
                color =  Color.White,
                fontSize = 15.sp
            )
        }
    }
}