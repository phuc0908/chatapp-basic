package com.example.chatapp.screens


import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.analytics.AnalyticsListener
import com.fatherofapps.jnav.annotations.JNav
import com.fatherofapps.jnav.annotations.JNavArg
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.toRepeatMode
import io.sanghun.compose.video.uri.VideoPlayerMediaItem
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@JNav(
    name = "MediaScreenNavigation",
    baseRoute = "media_mes",
    destination = "media_mes_destination",
    arguments = [
        JNavArg(name = "mediaUrl", type = String::class)
    ]
)

@Composable
fun MediaMessage(
    mediaUrl: String,
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .size(400.dp),
    ) {
        val repeatMode by remember { mutableStateOf(RepeatMode.NONE) }
        val linkRoot = "https://firebasestorage.googleapis.com/v0/b/chatapp-4e975.appspot.com/o/messages%2F"
        val url = linkRoot + URLDecoder.decode(mediaUrl, StandardCharsets.UTF_8.toString())
        Log.d("DECODE",url)

        val samplePlayList = listOf(
            VideoPlayerMediaItem.NetworkMediaItem(
                url = "$url.mp4",
                mediaMetadata = MediaMetadata.Builder().build(),
                mimeType = MimeTypes.VIDEO_MP4,
            )
        )
        VideoPlayer(
            mediaItems = samplePlayList,
            handleLifecycle = false,
            autoPlay = true,
            usePlayerController = true,
            controllerConfig = VideoPlayerControllerConfig.Default.copy(
                showSubtitleButton = true,
                showNextTrackButton = true,
                showBackTrackButton = true,
                showBackwardIncrementButton = true,
                showForwardIncrementButton = true,
                showRepeatModeButton = true,
                showFullScreenButton = false,
            ),
            repeatMode = repeatMode,
            onCurrentTimeChanged = {
                Log.d("CurrentTime", it.toString())
            },
            playerInstance = {
                Log.e("VOLUME", volume.toString())
                addAnalyticsListener(object : AnalyticsListener {
                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onRepeatModeChanged(
                        eventTime: AnalyticsListener.EventTime,
                        rMode: Int,
                    ) {
                        Toast.makeText(
                            context,
                            "RepeatMode changed = ${rMode.toRepeatMode()}",
                            Toast.LENGTH_LONG,
                        )
                            .show()
                    }

                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onPlayWhenReadyChanged(
                        eventTime: AnalyticsListener.EventTime,
                        playWhenReady: Boolean,
                        reason: Int,
                    ) {
                        Toast.makeText(
                            context,
                            "isPlaying = $playWhenReady",
                            Toast.LENGTH_LONG,
                        )
                            .show()
                    }

                    @SuppressLint("UnsafeOptInUsageError")
                    override fun onVolumeChanged(
                        eventTime: AnalyticsListener.EventTime,
                        volume: Float,
                    ) {
                        Toast.makeText(
                            context,
                            "Player volume changed = $volume",
                            Toast.LENGTH_LONG,
                        )
                            .show()
                    }
                })
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
