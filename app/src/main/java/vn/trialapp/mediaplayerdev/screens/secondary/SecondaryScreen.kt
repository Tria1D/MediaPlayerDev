package vn.trialapp.mediaplayerdev.screens.secondary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import vn.trialapp.mediaplayerdev.ui.components.BottomPlayerUI
import vn.trialapp.mediaplayerdev.viewmodels.MediaViewModel

@Composable
fun SecondaryScreen (
    mediaViewModel: MediaViewModel    
) {
    Scaffold(bottomBar = {
        BottomPlayerUI(
            durationString = mediaViewModel.formatDuration(mediaViewModel.duration),
            playResourceProvider = { 
                if (mediaViewModel.isPlaying) android.R.drawable.ic_media_pause 
                else android.R.drawable.ic_media_play 
            },
            progressProvider = { Pair(mediaViewModel.progress, mediaViewModel.progressString) },
            onUiEvent = mediaViewModel::onUiEvent
        )
    }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues)
        ) {
            Image(painter = rememberAsyncImagePainter(model = "https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize())
        }
    }
}