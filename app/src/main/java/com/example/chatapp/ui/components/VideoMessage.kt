package com.example.chatapp.ui.components

import android.content.Context
import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.chatapp.R


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoMessage(
    mediaUrl: String,
    isMyVideo: Boolean,
    openMedia:()-> Unit,
    onLongPress: ()-> Unit,
    context: Context
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
                .combinedClickable(
                    onClick = {
                        openMedia()
                    },
                    onLongClick = onLongPress
                )

                .align(if (isMyVideo) Alignment.End else Alignment.Start)
        ) {
            VideoMesPlayer(
                videoUri = Uri.parse(mediaUrl),
                modifier = Modifier.sizeIn(maxWidth = 200.dp),
                context = context
            )
        }

    }
}

@Composable
fun VideoMesPlayer(videoUri: Uri, modifier: Modifier = Modifier, context: Context) {
    var videoAspectRatio by remember { mutableFloatStateOf(2f / 2f) }
    var isPlaying by remember { mutableStateOf(true) }
    var isVideoPrepared by remember { mutableStateOf(false) }
    val videoView = remember { VideoView(context) }

    AndroidView(
        modifier = modifier
            .then(Modifier
                .aspectRatio(videoAspectRatio)
            ),
        factory = {
            videoView.apply {
                setVideoURI(videoUri)
                setOnPreparedListener { mp ->
                    isVideoPrepared = true
                    videoAspectRatio = mp.videoWidth.toFloat() / mp.videoHeight
                }
            }
        },
        update = {
            if (isPlaying) {
                videoView.start()
            } else {
                videoView.pause()
            }
        },
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
