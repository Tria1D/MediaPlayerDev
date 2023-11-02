package vn.trialapp.mediaplayerdev.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import vn.trialapp.mediaplayerdev.viewmodels.MediaViewModel
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import vn.trialapp.mediaplayerdev.route.Destination
import vn.trialapp.mediaplayerdev.ui.components.PlayerUi
import vn.trialapp.mediaplayerdev.viewmodels.MediaUiState

@Composable
internal fun MainScreen(
    mediaViewModel: MediaViewModel,
    navController: NavController,
    startService: () -> Unit
) {
    val state = mediaViewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (state.value) {

            is MediaUiState.Initial -> CircularProgressIndicator(
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
            )

            is MediaUiState.Ready -> {
                LaunchedEffect(key1 = true) {
                    startService()
                }
                ReadyContent(mediaViewModel = mediaViewModel, navController = navController)
            }
        }
    }
}

@Composable
private fun ReadyContent(
    mediaViewModel: MediaViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerUi(
            durationString = mediaViewModel.formatDuration(mediaViewModel.duration),
            playResourceProvider = {
                if (mediaViewModel.isPlaying) android.R.drawable.ic_media_pause
                else android.R.drawable.ic_media_play
            },
            progressProvider = { Pair(mediaViewModel.progress, mediaViewModel.progressString) },
            onUiEvent = mediaViewModel::onUiEvent
        )

        FloatingActionButton(
            onClick = { navController.navigate(Destination.Secondary.route) },
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Navigate to Secondary",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}