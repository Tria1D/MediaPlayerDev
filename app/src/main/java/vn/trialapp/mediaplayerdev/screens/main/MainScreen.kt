package vn.trialapp.mediaplayerdev.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import vn.trialapp.mediaplayerdev.viewmodels.MediaViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import vn.trialapp.mediaplayerdev.route.Destination
import vn.trialapp.mediaplayerdev.ui.components.ImageDiskUi
import vn.trialapp.mediaplayerdev.ui.components.PlayerUi
import vn.trialapp.mediaplayerdev.ui.components.SearchBar
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
        MediaSearchBar(
            leadingIcon = Pair(Icons.Default.Search, "Search Icon"),
            trailingIcon = Pair(Icons.Default.Close, "Close Icon"),
            historyIcon = Pair(Icons.Default.History, "History Icon"),
            onSearchClicked = mediaViewModel::requestSearch
        )

        when (state.value) {
            is MediaUiState.None -> { }

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
        ImageDiskUi(
            modifier = Modifier.padding(bottom = 20.dp),
            isPlaying = mediaViewModel.isPlaying,
            imageUrl = mediaViewModel.imageUrl
        )

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

@Suppress("NAME_SHADOWING")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MediaSearchBar(
    leadingIcon: Pair<ImageVector, String>,
    trailingIcon: Pair<ImageVector, String>,
    historyIcon: Pair<ImageVector, String>,
    onSearchClicked: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val itemsSearched = remember { mutableStateListOf("") }

    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        query = searchText,
        onQueryChange = {
            searchText = it
        },
        onSearch = {
            onSearchClicked(searchText)
            itemsSearched.add(searchText)
            isActive = false
            searchText = ""
        },
        active = isActive,
        onActiveChange = {
            isActive = it
        },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon.first,
                contentDescription = leadingIcon.second
            )
        },
        trailingIcon = {
            if (isActive) {
                Icon(
                    modifier = Modifier.clickable {
                        if (searchText.isNotEmpty()) {
                            searchText = ""
                        } else {
                            isActive = false
                        }
                    },
                    imageVector = trailingIcon.first,
                    contentDescription = trailingIcon.second
                )
            }
        }
    ) {
        if (itemsSearched.size > 1) {
            for (i in 1 until itemsSearched.size) {
                Row(
                    modifier = Modifier.padding(all = 14.dp)
                ) {

                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = historyIcon.first,
                        contentDescription = historyIcon.second
                    )

                    Text(text = itemsSearched[i])
                }
            }
        }
    }
}