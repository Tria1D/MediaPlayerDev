package vn.trialapp.mediaplayerdev.features.songdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.models.SongDetailUiModel
import vn.trialapp.mediaplayerdev.ui.components.ImageDiskUi
import vn.trialapp.mediaplayerdev.ui.components.PlayerUi
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme

@Composable
internal fun SongDetailScreen(
    viewModel: SongDetailViewModel = hiltViewModel(),
    index: Int,
    startService: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (uiState.value) {
            is SongDetailUiState.None -> {
                viewModel.requestGetSongDetailFirebase(index)
            }

            is SongDetailUiState.Initial -> CircularProgressIndicator(
                modifier = Modifier
                    .size(dimensionResource(R.dimen.mpd_song_detail_scr_circular_progress_size))
                    .align(Alignment.Center)
            )
            is SongDetailUiState.Ready -> {
                LaunchedEffect(key1 = true) {
                    startService()
                }
                val song = (uiState.value as SongDetailUiState.Ready).song
                val uiModel = SongDetailUiModel(
                    imageUrl = song.imageUrl,
                    songName = song.title,
                    songArtist = song.artist,
                    isPlaying = viewModel.isPlaying,
                    durationString = viewModel.formatDuration(viewModel.duration),
                    progress = viewModel.progress,
                    progressString = viewModel.progressString,
                    onUiEvent = viewModel::onUiEvent
                )

                SongDetailContent(uiModel = uiModel)
            }
        }
    }
}

@Composable
private fun SongDetailContent(
    uiModel: SongDetailUiModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = dimensionResource(R.dimen.mpd_song_detail_scr_playing_now_padding_vertical))
                ,
                text = stringResource(R.string.mdp_song_detail_scr_playing_now)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = dimensionResource(R.dimen.mpd_song_detail_scr_box_image_disk_padding_top))
            ) {
                ImageDiskUi(
                    modifier = Modifier
                        .padding(bottom = dimensionResource(R.dimen.mpd_song_detail_scr_image_disk_padding_bottom))
                        .align(Alignment.Center),
                    isPlaying = uiModel.isPlaying,
                    imageUrl = uiModel.imageUrl
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = dimensionResource(R.dimen.mpd_song_detail_scr_song_name_padding_top)),
                text = uiModel.songName,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = dimensionResource(R.dimen.mpd_song_detail_scr_song_artist_padding_top)),
                text = uiModel.songArtist,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            PlayerUi(
                durationString = uiModel.durationString,
                playResourceProvider = {
                    if (uiModel.isPlaying) android.R.drawable.ic_media_pause
                    else android.R.drawable.ic_media_play
                },
                progressProvider = { Pair(uiModel.progress, uiModel.progressString) },
                onUiEvent = uiModel.onUiEvent
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSongDetailContent() {
    val fakeModel = SongDetailUiModel(
        imageUrl = "",
        isPlaying = true,
        durationString = "03:35",
        progress = 0.5f,
        progressString = "01:47 / 03:35",
        onUiEvent = {}
    )

    MediaPlayerDevTheme {
        SongDetailContent(uiModel = fakeModel)
    }
}