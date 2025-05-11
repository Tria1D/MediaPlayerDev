package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.times
import vn.trialapp.mediaplayerdev.R
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.INITIAL_ITEMS_SEARCHED
import vn.trialapp.mediaplayerdev.utils.AppConstants.Common.INITIAL_SEARCH_TEXT

@Suppress("NAME_SHADOWING")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaSearchBar(
    modifier: Modifier,
    leadingIcon: Pair<ImageVector, String>,
    trailingIcon: Pair<ImageVector, String>,
    historyIcon: Pair<ImageVector, String>,
    onSearchClicked: (String) -> Unit = {}
) {
    var searchText by remember { mutableStateOf(INITIAL_SEARCH_TEXT) }
    var isActive by remember { mutableStateOf(false) }
    val itemsSearched = remember { mutableStateListOf(INITIAL_ITEMS_SEARCHED) }
    val maxItemsToShow = 4
    val itemHeight = dimensionResource(R.dimen.mpd_search_bar_item_height)
    val additionalHeight = if ((itemsSearched.size - 1) < 2 && (itemsSearched.size - 1) > 0) {
        dimensionResource(R.dimen.mpd_search_bar_additional_item_height_less_than_one)
    } else {
        ((itemsSearched.size - 1).coerceAtMost(maxItemsToShow)) * dimensionResource(R.dimen.mpd_search_bar_additional_item_height_more_than_one)
    }
    SearchBar(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(R.dimen.mpd_search_bar_rounded_corner_size)))
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
            Text(text = stringResource(R.string.mdp_media_search_bar_placeholder_text))
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
                            searchText = INITIAL_SEARCH_TEXT
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
                        .padding(all = dimensionResource(R.dimen.mpd_search_bar_item_searched_row_padding_all))
                        .wrapContentHeight()
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(R.dimen.mpd_search_bar_item_searched_icon_padding_start),
                                end = dimensionResource(R.dimen.mpd_search_bar_item_searched_icon_padding_end)
                            ),
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
    MediaPlayerDevTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderBackground()
            MediaSearchBar(
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.mpd_song_scr_media_search_bar_padding_start),
                    top = dimensionResource(R.dimen.mpd_song_scr_media_search_bar_padding_top),
                    end = dimensionResource(R.dimen.mpd_song_scr_media_search_bar_padding_end)
                ),
                leadingIcon = Pair(Icons.Default.Search, stringResource(R.string.mdp_song_scr_media_search_bar_leading_icon)),
                trailingIcon = Pair(Icons.Default.Close, stringResource(R.string.mdp_song_scr_media_search_bar_trailing_icon)),
                historyIcon = Pair(Icons.Default.History, stringResource(R.string.mdp_song_scr_media_search_bar_history_icon)),
                onSearchClicked = { }
            )
        }
    }
}