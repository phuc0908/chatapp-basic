package com.example.chatapp.ui.components

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.chatapp.R


@Composable
fun VideoMessage(
    mediaUrl: String,
    isMyVideo: Boolean,
    openMedia:()-> Unit,
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
                .clickable {
                    openMedia()
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
    var isPlaying by remember { mutableStateOf(true) }
//    var isSoundEnabled by remember { mutableStateOf(true) }
    var isVideoPrepared by remember { mutableStateOf(false) }

    AndroidView(
        modifier = modifier
            .then(Modifier
                .aspectRatio(videoAspectRatio)
            ),
        factory = {
            VideoView(context).apply {
                setVideoURI(videoUri)
                setOnPreparedListener { mp ->
                    isVideoPrepared = true
                    videoAspectRatio = mp.videoWidth.toFloat() / mp.videoHeight
                    mp.setVolume(0.5f, 0.5f)
                }
            }
        },
        update = {videoView->
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

//        if (isSoundEnabled){
//            RoundIconButton(
//                R.drawable.pause_video,
//                imageVector = null,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(top = 100.dp)
//                    .size(43.dp)
//                    .aspectRatio(1f),
//                onClick = {
//                    isSoundEnabled = !isSoundEnabled
//                },
//            )
//        }else{
//            RoundIconButton(
//                R.drawable.start_video,
//                imageVector = null,
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(top = 100.dp)
//                    .size(43.dp)
//                    .aspectRatio(1f),
//                onClick = {
//                    isSoundEnabled = !isSoundEnabled
//                },
//            )
//        }
    }
}

fun enableSound(sound: Int, mp: MediaPlayer, context: Context) {
    val f = java.lang.Float.valueOf(sound.toFloat())
    Log.e("checkingsounds", "&&&&&   $f")
    mp.setVolume(f, f)
    mp.isLooping = true
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) //Max Volume 15
    audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) //this will return current volume.
    audioManager.setStreamVolume(
        AudioManager.STREAM_MUSIC,
        sound,
        AudioManager.FLAG_PLAY_SOUND
    )
}