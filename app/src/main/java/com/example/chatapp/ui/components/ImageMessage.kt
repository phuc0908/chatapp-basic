package com.example.chatapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ImageMessage(
    imageUrl: String,
    isMyImage: Boolean,
    onLongPress: ()-> Unit
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(coil.size.Size.ORIGINAL)
            .build()
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
//                Long press
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            onLongPress()
                        }
                    )
                }
                .align(if (isMyImage) Alignment.End else Alignment.Start)
        ) {
            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator()
            }else{
                Image(
                    painter = painter,
                    contentScale = ContentScale.Fit,
                    contentDescription = "image",
                    modifier = Modifier
                        .sizeIn(maxWidth = 300.dp)
                )
            }

        }

    }
}

@Preview
@Composable
fun PreviewImageMessage() {
    ImageMessage(
        imageUrl = "https://your-image-url.com/image.jpg",
        isMyImage = true,
        {}
    )
}
