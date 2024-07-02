package vn.trialapp.mediaplayerdev.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import vn.trialapp.mediaplayerdev.viewmodels.MediaViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import vn.trialapp.mediaplayerdev.ui.components.*
import vn.trialapp.mediaplayerdev.ui.components.SearchBar
import vn.trialapp.mediaplayerdev.ui.theme.DenimBlue
import vn.trialapp.mediaplayerdev.viewmodels.MediaUiState

@Composable
internal fun MainScreen(
    mediaViewModel: MediaViewModel,
    startService: () -> Unit
) {
    val state = mediaViewModel.uiState.collectAsStateWithLifecycle()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        HeaderBackground()
        MediaSearchBar(
            modifier = Modifier.padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp).align(Alignment.TopCenter),
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
                BackgroundImageUi(imageUrl = mediaViewModel.imageUrl)
                ReadyContent(mediaViewModel = mediaViewModel)
            }
        }
    }
}

@Composable
private fun ReadyContent(
    mediaViewModel: MediaViewModel
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageDiskUi(
            modifier = Modifier.padding(bottom = 20.dp).align(Alignment.Center),
            isPlaying = mediaViewModel.isPlaying,
            imageUrl = mediaViewModel.imageUrl
        )

        PlayerUi(
            modifier = Modifier.align(Alignment.BottomCenter),
            durationString = mediaViewModel.formatDuration(mediaViewModel.duration),
            playResourceProvider = {
                if (mediaViewModel.isPlaying) android.R.drawable.ic_media_pause
                else android.R.drawable.ic_media_play
            },
            progressProvider = { Pair(mediaViewModel.progress, mediaViewModel.progressString) },
            onUiEvent = mediaViewModel::onUiEvent
        )
    }
}

@Suppress("NAME_SHADOWING")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MediaSearchBar(
    modifier: Modifier,
    leadingIcon: Pair<ImageVector, String>,
    trailingIcon: Pair<ImageVector, String>,
    historyIcon: Pair<ImageVector, String>,
    onSearchClicked: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    val itemsSearched = remember { mutableStateListOf("") }
    val maxItemsToShow = 4
    val itemHeight = 70.dp
    val additionalHeight = if ((itemsSearched.size - 1) < 2 && (itemsSearched.size - 1) > 0) {
        60.dp
    } else {
        ((itemsSearched.size - 1).coerceAtMost(maxItemsToShow)) * 50.dp
    }
    SearchBar(
        modifier = modifier
            .clip(RoundedCornerShape(25.dp))
            .heightIn(min = itemHeight, max = itemHeight + additionalHeight)
            .wrapContentHeight(),
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
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .wrapContentHeight()
                ) {
                    Icon(
                        modifier = Modifier.padding(start = 5.dp, end = 10.dp),
                        imageVector = historyIcon.first,
                        contentDescription = historyIcon.second
                    )
                    Text(text = itemsSearched[i])
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMediaSearchBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DenimBlue)
    ) {
        HeaderBackground()
        MediaSearchBar(
            modifier = Modifier.padding(
                start = 20.dp,
                top = 20.dp,
                end = 20.dp
            ),
            leadingIcon = Pair(Icons.Default.Search, "Search Icon"),
            trailingIcon = Pair(Icons.Default.Close, "Close Icon"),
            historyIcon = Pair(Icons.Default.History, "History Icon"),
            onSearchClicked = { }
        )
    }
}