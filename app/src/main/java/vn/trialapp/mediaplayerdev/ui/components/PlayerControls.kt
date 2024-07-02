package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.viewmodels.MediaUiEvent

@Composable
internal fun PlayerControls(
    playResourceProvider: () -> Int,
    onUiEvent: (MediaUiEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(35.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(android.R.drawable.ic_media_rew),
            contentDescription = "Backward Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(MediaUiEvent.Backward) })
                .padding(12.dp)
                .size(34.dp)
        )
        Image(
            painter = painterResource(id = playResourceProvider()),
            contentDescription = "Play/Pause Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(MediaUiEvent.PlayPause) })
                .padding(8.dp)
                .size(56.dp)
        )
        Icon(
            painter = painterResource(android.R.drawable.ic_media_ff),
            contentDescription = "Forward Button",
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = { onUiEvent(MediaUiEvent.Forward) })
                .padding(12.dp)
                .size(34.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewPlayerControls() {
    MediaPlayerDevTheme {
        PlayerControls(
            playResourceProvider = { android.R.drawable.ic_media_pause },
            onUiEvent = { })
    }
}