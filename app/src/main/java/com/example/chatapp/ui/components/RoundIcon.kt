package com.example.chatapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chatapp_dacs3.ui.theme.Green1

@Composable
fun RoundIconButton(
    imageResId: Int?,
    imageVector: ImageVector?,
    modifier: Modifier,
    onClick: () -> Unit,

    ) {
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
                        if(imageResId != null){

                            Image(
                                painter = painterResource(id = imageResId),
                                contentScale = ContentScale.Crop,

                                contentDescription = "",
                                modifier = modifier
                            )
                        }
                        else if(imageVector != null){
                            Icon(
                                imageVector = imageVector,
                                contentDescription = "",
                                tint = Green1,
                                modifier = modifier
                            )
                        }
                    },)
            }
        )
    }
}