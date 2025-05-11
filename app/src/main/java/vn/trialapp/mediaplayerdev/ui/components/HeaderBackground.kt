package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.Common.Black
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme

@Composable
fun HeaderBackground() {
    Box(modifier = Modifier
        .clip(
            RoundedCornerShape(
                bottomStart = dimensionResource(R.dimen.mpd_header_rounded_corner_bottom_start),
                bottomEnd = dimensionResource(R.dimen.mpd_header_rounded_corner_bottom_end)
            )
        )
        .heightIn(dimensionResource(R.dimen.mpd_header_height_in))
        .fillMaxWidth()
        .background(Black)
    )
}

@Preview
@Composable
private fun PreviewHeaderBackground() {
    MediaPlayerDevTheme {
        HeaderBackground()
    }
}