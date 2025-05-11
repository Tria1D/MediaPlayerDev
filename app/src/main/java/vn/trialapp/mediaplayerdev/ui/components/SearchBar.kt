package vn.trialapp.mediaplayerdev.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.material.Icon
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import vn.trialapp.mediaplayerdev.ui.theme.Common.AliceBlue
import vn.trialapp.mediaplayerdev.ui.theme.MediaPlayerDevTheme


@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = SearchBarDefaults.inputFieldShape,
    colors: SearchBarColors = SearchBarDefaults.colors(
        containerColor = AliceBlue
    ),
    tonalElevation: Dp = SearchBarDefaults.Elevation,
    windowInsets: WindowInsets = SearchBarDefaults.windowInsets,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        modifier = modifier,
        enabled = enabled,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = shape,
        colors = colors,
        tonalElevation = tonalElevation,
        windowInsets = windowInsets,
        interactionSource = interactionSource,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewSearchBar() {
    var text by remember { mutableStateOf("") }
    val itemsSearched = remember { mutableStateListOf("","Sample text") }
    val historyIcon = Pair(Icons.Default.History, "History Icon")
    val isActive by remember { mutableStateOf(true) }
    val maxItemsToShow = 4
    val itemHeight = 70.dp
    val additionalHeight = ((itemsSearched.size - 1).coerceAtMost(maxItemsToShow)) * itemHeight

    MediaPlayerDevTheme {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(30.dp))
                .border(
                    BorderStroke(
                        0.1.dp,
                        SolidColor(MaterialTheme.colorScheme.onSurface)
                    ),
                    RoundedCornerShape(12.dp)
                )
                .heightIn(min = itemHeight, max = itemHeight + additionalHeight)
                .wrapContentHeight(),
            query = text,
            onQueryChange = { text = it },
            onSearch = { },
            active = isActive,
            onActiveChange = { },
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon"
                )
            }
        ) {
            if (itemsSearched.size > 1) {
                for (i in 1 until itemsSearched.size) {
                    Row(
                        modifier = Modifier
                            .padding(all = 14.dp)
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
}