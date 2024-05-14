package com.example.chatapp_dacs3.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatapp_dacs3.ui.theme.LightGreen1

@Composable
fun ImageMessage(
    imageResId: Int,
    isMyImage: Boolean
) {
    Column(
        modifier = Modifier
            .width(400.dp)
            .padding(
                start = if (isMyImage) 50.dp else 7.dp,
                end = if (isMyImage) 7.dp else 50.dp,
                top = 5.dp,
                bottom = 5.dp
            ),
        horizontalAlignment =
        if (isMyImage)
            Alignment.End
        else
            Alignment.Start,

    ) {
        Box{
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize(),
                alignment =
                if (isMyImage)
                    Alignment.CenterEnd
                else
                    Alignment.CenterStart
            )
        }

    }
}