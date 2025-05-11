package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.Common.AliceBlue
import vn.trialapp.mediaplayerdev.ui.theme.Common.AzureishWhite
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme

@Composable
internal fun PlayerControlsIconButton(
    iconImage: Painter,
    contentDescription: String = "",
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(R.dimen.mpd_player_controls_icon_button_container_size))
            .clip(CircleShape)
            .background(AliceBlue)
            .border(
                dimensionResource(R.dimen.mpd_player_controls_icon_button_container_border_width),
                AzureishWhite,
                CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = iconImage,
            contentDescription = contentDescription,
            modifier = Modifier
                .clip(CircleShape)
                .padding(dimensionResource(R.dimen.mpd_player_controls_icon_button_icon_padding_all))
                .size(dimensionResource(R.dimen.mpd_player_controls_icon_button_icon_size))
        )
    }
}

@Preview
@Composable
private fun PreviewPlayerControlsIconButton() {
    MediaPlayerDevTheme {
        PlayerControlsIconButton(
            iconImage = painterResource(android.R.drawable.ic_media_rew),
            onClick = {}
        )
    }
}