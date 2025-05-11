package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.features.songdetail.SongDetailUiEvent

@Composable
internal fun PlayerControls(
    playResourceProvider: () -> Int,
    onUiEvent: (SongDetailUiEvent) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.mpd_player_controls_row_container_space_horizontal)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerControlsIconButton(
            iconImage = painterResource(android.R.drawable.ic_media_rew),
            contentDescription = stringResource(R.string.mdp_player_controls_backward_button),
            onClick = { onUiEvent(SongDetailUiEvent.Backward) }
        )

        PlayerControlsImageButton(
            image =  painterResource(id = playResourceProvider()),
            contentDescription = stringResource(R.string.mdp_player_controls_play_pause_button),
            onClick = { onUiEvent(SongDetailUiEvent.PlayPause) }
        )

        PlayerControlsIconButton(
            iconImage = painterResource(android.R.drawable.ic_media_ff),
            contentDescription = stringResource(R.string.mdp_player_controls_forward_button),
            onClick = { onUiEvent(SongDetailUiEvent.Forward) }
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