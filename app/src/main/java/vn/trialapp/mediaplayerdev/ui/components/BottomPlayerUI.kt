package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.viewmodels.MediaUiEvent

@Composable
fun BottomPlayerUI(
    modifier: Modifier = Modifier,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (MediaUiEvent) -> Unit
) {
    val (progress, progressString) = progressProvider()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
        )

        PlayerBar(
            progress = progress,
            durationString = durationString,
            progressString = progressString,
            onUiEvent = onUiEvent
        )
        PlayerControls(
            playResourceProvider = playResourceProvider,
            onUiEvent = onUiEvent
        )
    }
}

@Preview
@Composable
private fun PreviewBottomPlayerUI() {
    MediaPlayerDevTheme {
        BottomPlayerUI(
            durationString = "3:30",
            playResourceProvider = { android.R.drawable.ic_media_pause },
            progressProvider = { Pair(0.7f, "2:30") },
            onUiEvent = { }
        )
    }
}