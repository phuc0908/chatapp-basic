package com.example.chatapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.ui.theme.LightGreen1

@Composable
fun calculateBoxHeight(numberOfLines: Int): Dp {
    val lineHeight = 20.sp.toDp()
    val padding = 10.dp
    val textHeight = lineHeight * numberOfLines
    return textHeight + padding
}
@Composable
fun TextUnit.toDp(): Dp {
    return with(LocalDensity.current) { toDp() }
}

@Composable
fun Message(
    message: String ,
    isMyMessage: Boolean,
    onLongPress: ()-> Unit
) {
    val line = message.lines().size
    val boxHeight = calculateBoxHeight(line)

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
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress()
                        }
                    )
                }
                .background(
                    if (isMyMessage)
                    LightGreen1
                    else
                    Color.LightGray
                )
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
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
                lineHeight = 20.sp,
                style = TextStyle(
                    fontSize = 14.sp,
                    color =
                        if (isMyMessage)
                            Color.White
                        else
                            Color.Black
                    ,
                ))
        }
    }
}


