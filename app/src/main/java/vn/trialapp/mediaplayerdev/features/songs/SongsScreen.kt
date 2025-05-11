package vn.trialapp.mediaplayerdev.features.songs

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.models.Song
import vn.trialapp.mediaplayerdev.router.AppRouter
import vn.trialapp.mediaplayerdev.ui.components.*
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.utils.LogUtil

@Composable
internal fun SongsScreen(
    viewModel: SongsViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    SongsScreenContent(state.value, navController)
}

@Composable
fun SongsScreenContent(
    uiState: SongsUiState,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(Modifier.fillMaxWidth()) {
            HeaderBackground()
            MediaSearchBar(
                modifier = Modifier
                    .padding(
                        start = dimensionResource(R.dimen.mpd_song_scr_media_search_bar_padding_start),
                        top = dimensionResource(R.dimen.mpd_song_scr_media_search_bar_padding_top),
                        end = dimensionResource(R.dimen.mpd_song_scr_media_search_bar_padding_end)
                    )
                    .align(Alignment.TopCenter),
                leadingIcon = Pair(Icons.Default.Search, stringResource(R.string.mdp_song_scr_media_search_bar_leading_icon)),
                trailingIcon = Pair(Icons.Default.Close, stringResource(R.string.mdp_song_scr_media_search_bar_trailing_icon)),
                historyIcon = Pair(Icons.Default.History, stringResource(R.string.mdp_song_scr_media_search_bar_history_icon)),
                onSearchClicked = { }
            )
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.mpd_song_scr_spacer_between_search_bar_and_content)))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (uiState) {
                is SongsUiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.mpd_song_scr_circular_progress_size))
                        .align(Alignment.Center)
                )

                is SongsUiState.Success -> {
                    val songList = uiState.songs
                    SongList(songList) { index ->
                        navController.navigate(AppRouter.SongDetail.createRoute(index))
                    }
                }

                is SongsUiState.Error -> {
                    LogUtil.d(uiState.message)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSongsScreenContent() {
    val dummySongs = listOf(
        Song(
            mediaId = "1",
            title = "Imagine",
            artist = "John Lennon"
        ),
        Song(
            mediaId = "2",
            title = "Bohemian Rhapsody",
            artist = "Queen"
        )
    )

    val dummyState = SongsUiState.Success(dummySongs)
    val navController = rememberNavController()

    MediaPlayerDevTheme(darkTheme = false, dynamicColor = false) {
        SongsScreenContent(uiState = dummyState, navController = navController)
    }
}