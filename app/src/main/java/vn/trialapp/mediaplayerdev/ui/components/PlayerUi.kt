package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.features.songdetail.SongDetailUiEvent

@Composable
fun PlayerUi(
    modifier: Modifier = Modifier,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (SongDetailUiEvent) -> Unit
) {
    val (progress, progressString) = progressProvider()

    Box(
        modifier = modifier
            .padding(dimensionResource(R.dimen.mpd_player_ui_box_container_padding_all))
            .background(color = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
}

@Preview
@Composable
private fun PreviewPlayerUi() {
    MediaPlayerDevTheme {
        PlayerUi(
            durationString = "3:30",
            playResourceProvider = { android.R.drawable.ic_media_pause },
            progressProvider = { Pair(0.7f, "2:30") },
            onUiEvent = { }
        )
    }
}