package com.example.chatapp.ui.components

import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.chatapp.R

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
            VideoMesPlayer(
                videoUri = Uri.parse(mediaUrl),
                modifier = Modifier.sizeIn(maxWidth = 200.dp)
            )
        }

    }
}

@Composable
fun VideoMesPlayer(videoUri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var videoAspectRatio by remember { mutableFloatStateOf(2f / 2f) }
    var isPlaying by remember { mutableStateOf(false) }
    var isVideoPrepared by remember { mutableStateOf(false) }

    AndroidView(
        modifier = modifier.then(Modifier.aspectRatio(videoAspectRatio)),
        factory = {
            VideoView(context).apply {
                setOnPreparedListener { mp ->
                    isVideoPrepared = true
                    videoAspectRatio = mp.videoWidth.toFloat() / mp.videoHeight
                }
                setVideoURI(videoUri)
            }
        },
        update = {videoView->
            if (isPlaying) {
                videoView.start()
            } else {
                videoView.pause()
            }
        }
    )
    if (isVideoPrepared) {
       if (isPlaying){
           RoundIconButton(
               R.drawable.pause_video,
               imageVector = null,
               modifier = Modifier
                   .fillMaxHeight()
                   .size(43.dp)
                   .aspectRatio(1f),
               onClick = {
                   isPlaying = !isPlaying
               },
           )
       }else{
           RoundIconButton(
               R.drawable.start_video,
               imageVector = null,
               modifier = Modifier
                   .fillMaxHeight()
                   .size(43.dp)
                   .aspectRatio(1f),
               onClick = {
                   isPlaying = !isPlaying
               },
           )
       }

    }
}