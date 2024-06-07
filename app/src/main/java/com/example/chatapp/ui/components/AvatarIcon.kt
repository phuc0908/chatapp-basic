package com.example.chatapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun AvatarIcon(
    imageUrl: String?,
    modifier: Modifier,
    isOnline: Boolean,
    onClick: () -> Unit,
) {
    Row {
        Box(modifier = Modifier.clip(CircleShape)){
            IconButton(
                onClick = onClick,
                modifier = modifier,
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(9.dp)
                            .clip(shape = CircleShape),
                        contentAlignment = Alignment.Center,
                        content = {
                            if(imageUrl != null){
                                Image(
                                    painter = rememberAsyncImagePainter(imageUrl),
                                    contentScale = ContentScale.Crop,
                                    contentDescription = "",
                                    modifier = modifier
                                )
                            }
                        },
                    )
                }
            )
        }
        if(isOnline){
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .offset(x = (-20).dp, y = 40.dp)
                    .background(Color.Green, CircleShape)
                    .border(1.5.dp, Color.White, CircleShape)
            )
        }else{
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .offset(x = (-20).dp, y = 40.dp)
            )
        }
    }
}