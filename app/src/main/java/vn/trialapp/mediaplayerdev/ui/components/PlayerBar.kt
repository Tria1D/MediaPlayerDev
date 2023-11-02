package vn.trialapp.mediaplayerdev.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import vn.trialapp.mediaplayerdev.viewmodels.MediaUiEvent

@Composable
internal fun PlayerBar(
    progress: Float,
    durationString: String,
    progressString: String,
    onUiEvent: (MediaUiEvent) -> Unit
) {
    val newProgressValue = remember { mutableStateOf(0f) }
    val isNewProgressValue = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Slider(
            value = if (isNewProgressValue.value) newProgressValue.value else progress,
            onValueChange = { newValue ->
                isNewProgressValue.value = true
                newProgressValue.value = newValue
                onUiEvent(MediaUiEvent.UpdateProgress(newProgress = newValue))
            },
            onValueChangeFinished = {
                isNewProgressValue.value = false
            },
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = progressString)
            Text(text = durationString)
        }
    }
}