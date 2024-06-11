package com.example.chatapp.ui.components


import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.analytics.AnalyticsListener
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.controller.VideoPlayerControllerConfig
import io.sanghun.compose.video.toRepeatMode
import io.sanghun.compose.video.uri.VideoPlayerMediaItem


@Composable
fun MediaMessage(
    mediaUrl: String,
    isMyVideo: Boolean,
    onLongPress: () -> Unit
) {
    val context = LocalContext.current
    val MIME_TYPE_DASH = MimeTypes.APPLICATION_MPD
    val MIME_TYPE_HLS = MimeTypes.APPLICATION_M3U8
    val MIME_TYPE_VIDEO_MP4 = MimeTypes.VIDEO_MP4

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val repeatMode by remember { mutableStateOf(RepeatMode.NONE) }

        val samplePlayList = listOf(
            VideoPlayerMediaItem.NetworkMediaItem(
                url = mediaUrl,
                mediaMetadata = MediaMetadata.Builder().setTitle("Clear DASH: Tears").build(),
                mimeType = MIME_TYPE_DASH,
            )
        )


        VideoPlayer(
            mediaItems = samplePlayList,
            handleLifecycle = false,
            autoPlay = true,
            usePlayerController = true,
            enablePipWhenBackPressed = true,
            enablePip = true,
            controllerConfig = VideoPlayerControllerConfig.Default.copy(
                showSubtitleButton = true,
                showNextTrackButton = true,
                showBackTrackButton = true,
                showBackwardIncrementButton = true,
                showForwardIncrementButton = true,
                showRepeatModeButton = true,
                showFullScreenButton = true,
            ),
            repeatMode = repeatMode,
            onCurrentTimeChanged = {
                Log.e("CurrentTime", it.toString())
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
