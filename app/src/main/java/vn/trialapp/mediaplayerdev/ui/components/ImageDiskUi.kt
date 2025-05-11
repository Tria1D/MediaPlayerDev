package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.Common.BlueBallerina
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.INITIAL_ROTATION
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.TARGET_ROTATION_PLAYING
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.TARGET_ROTATION_SLOW_DOWN
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.TWEEN_PLAYING_DURATION
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.TWEEN_SLOW_DOWN_DURATION

@Composable
fun ImageDiskUi(
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    imageUrl: String
) {
    // Allow resume on rotation
    var currentRotation by remember { mutableFloatStateOf(INITIAL_ROTATION) }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Infinite repeatable rotation when is playing
            rotation.animateTo(
                targetValue = currentRotation + TARGET_ROTATION_PLAYING,
                animationSpec = infiniteRepeatable(
                    animation = tween(TWEEN_PLAYING_DURATION, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        } else {
            if (currentRotation > 0f) {
                // Slow down rotation on pause
                rotation.animateTo(
                    targetValue = currentRotation + TARGET_ROTATION_SLOW_DOWN,
                    animationSpec = tween(
                        durationMillis = TWEEN_SLOW_DOWN_DURATION,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }
    Box (
        modifier = modifier
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(dimensionResource(R.dimen.mpd_image_disk_size))
                .clip(CircleShape)
                .border(
                    dimensionResource(R.dimen.mpd_image_disk_border_width),
                    BlueBallerina,
                    CircleShape
                )
                .rotate(rotation.value)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImageDiskUi() {
    val imagePainter = painterResource(id = R.drawable.mock_image)

    MediaPlayerDevTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.mpd_image_disk_size))
                    .clip(CircleShape)
                    .border(
                        dimensionResource(R.dimen.mpd_image_disk_border_width),
                        BlueBallerina,
                        CircleShape
                    )
            )
        }
    }
}