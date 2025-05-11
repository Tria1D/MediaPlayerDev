package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.features.songdetail.SongDetailUiEvent
import vn.trialapp.mediaplayerdev.ui.theme.Common.AzureishWhite
import vn.trialapp.mediaplayerdev.ui.theme.Common.JordyBlue
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.INITIAL_PLAYER_BAR_PROGRESS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlayerBar(
    progress: Float,
    durationString: String,
    progressString: String,
    onUiEvent: (SongDetailUiEvent) -> Unit
) {
    val newProgressValue = remember { mutableFloatStateOf(INITIAL_PLAYER_BAR_PROGRESS) }
    val isNewProgressValue = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = if (isNewProgressValue.value) newProgressValue.floatValue else progress,
            onValueChange = { newValue ->
                isNewProgressValue.value = true
                newProgressValue.floatValue = newValue
                onUiEvent(SongDetailUiEvent.UpdateProgress(newProgress = newValue))
            },
            onValueChangeFinished = {
                isNewProgressValue.value = false
            },
            modifier = Modifier
                .padding(horizontal = dimensionResource(R.dimen.mpd_slider_padding_horizontal)),
            colors = SliderDefaults.colors(
                activeTrackColor = JordyBlue
            ),
            thumb = {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.mpd_slider_thumb_size))
                        .background(JordyBlue, CircleShape)
                        .border(
                            dimensionResource(R.dimen.mpd_slider_thumb_border_width),
                            AzureishWhite,
                            CircleShape
                        )
                )
            }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.mpd_player_bar_row_progress_padding_horizontal))
        ) {
            Text(text = progressString)
            Text(text = durationString)
        }
    }
}

@Preview
@Composable
private fun PreviewPlayerBar() {
    MediaPlayerDevTheme {
        PlayerBar(
            progress = 0.7f,
            durationString = "3:30",
            progressString = "2:30",
            onUiEvent = { })
    }
}