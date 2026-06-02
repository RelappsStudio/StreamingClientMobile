package com.relapps.localstreaming.common.presentation.player

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    videoUrl: String,
    isPlaying: Boolean,
    onVideoEnded: () -> Unit,
    onProgressChanged: (Float) -> Unit,
    modifier: Modifier = Modifier) {

    val context = LocalContext.current

    //single player instance initialized for this composable
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    //listener to listen when video playback ends
    DisposableEffect(videoUrl) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()

        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED) {
                    onVideoEnded()
                }
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            onProgressChanged(0f)
        }
    }

    LaunchedEffect(isPlaying, ) {
        if (isPlaying) {
            exoPlayer.play()
        } else {
            exoPlayer.pause()
        }
    }

    LaunchedEffect(isPlaying, videoUrl) {
        if (isPlaying) {
            while (isActive) {
                if (exoPlayer.duration > 0) {
                    val currentPosition = exoPlayer.currentPosition.toFloat()
                    val totalDuration = exoPlayer.duration.toFloat()

                    onProgressChanged((currentPosition / totalDuration).coerceIn(0f, 1f))
                }
                delay(16) //time to render 1 frame in 60fps, no need to run updates faster for now
            }
        }
    }


    //when player screen closes - free memory allocation for it
    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }
    //load view component into compose element
    AndroidView(
        factory = {context ->
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            }
        },
        modifier = modifier.fillMaxSize()
    )
}