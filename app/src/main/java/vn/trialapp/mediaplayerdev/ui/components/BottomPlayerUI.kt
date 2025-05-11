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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.features.songdetail.SongDetailUiEvent
import vn.trialapp.mediaplayerdev.ui.theme.Common.Black

@Composable
fun BottomPlayerUI(
    modifier: Modifier = Modifier,
    durationString: String,
    playResourceProvider: () -> Int,
    progressProvider: () -> Pair<Float, String>,
    onUiEvent: (SongDetailUiEvent) -> Unit
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
                .height(dimensionResource(R.dimen.mpd_bottom_player_ui_divider_height))
                .background(Black)
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