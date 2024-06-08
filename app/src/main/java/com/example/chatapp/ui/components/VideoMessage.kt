package com.example.chatapp.ui.components

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun VideoMessage(
    mediaUrl: String,
    isMyVideo: Boolean,
    onLongPress: ()-> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)
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
                .align(if (isMyVideo) Alignment.End else Alignment.Start)
        ) {
            VideoPlayer(
                videoUri = Uri.parse(mediaUrl),
                modifier = Modifier.sizeIn(maxWidth = 200.dp)
            )
        }

    }
}

@Composable
fun VideoPlayer(videoUri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var videoAspectRatio by remember { mutableStateOf(9f / 9f) }

    AndroidView(
        modifier = modifier.then(Modifier.aspectRatio(videoAspectRatio)),
        factory = {
            VideoView(context).apply {
                setVideoURI(videoUri)
                setOnPreparedListener { mp ->
                    videoAspectRatio = mp.videoWidth.toFloat() / mp.videoHeight
                    mp.isLooping = true
                    start()
                }
            }
        },
        update = {
            it.setVideoURI(videoUri)
            it.start()
        }
    )
}