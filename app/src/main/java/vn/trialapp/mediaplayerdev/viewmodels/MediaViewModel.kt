package vn.trialapp.mediaplayerdev.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.trialapp.mediaplayerdev.service.MediaServiceHandler
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.trialapp.mediaplayerdev.service.MediaServiceState
import vn.trialapp.mediaplayerdev.service.PlayerEvent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaServiceHandler: MediaServiceHandler,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }

    private val _uiState = MutableStateFlow<MediaUiState>(MediaUiState.Initial)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadData()

            mediaServiceHandler.mediaState.collect { mediaState ->
                when (mediaState) {
                    is MediaServiceState.Buffering -> calculateProgressValues(mediaState.progress)
                    is MediaServiceState.Initial -> _uiState.value = MediaUiState.Initial
                    is MediaServiceState.Playing -> isPlaying = mediaState.isPlaying
                    is MediaServiceState.Progress -> calculateProgressValues(mediaState.progress)
                    is MediaServiceState.Ready -> {
                        duration = mediaState.duration
                        _uiState.value = MediaUiState.Ready
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            mediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
        }
    }

    fun onUiEvent(uiEvent: MediaUiEvent) = viewModelScope.launch {
        when (uiEvent) {
            is MediaUiEvent.Backward -> mediaServiceHandler.onPlayerEvent(PlayerEvent.Backward)
            is MediaUiEvent.Forward -> mediaServiceHandler.onPlayerEvent(PlayerEvent.Forward)
            is MediaUiEvent.PlayPause -> mediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
            is MediaUiEvent.UpdateProgress -> {
                progress = uiEvent.newProgress
                mediaServiceHandler.onPlayerEvent(
                    PlayerEvent.UpdateProgress(
                        uiEvent.newProgress
                    )
                )
            }
        }
    }

    private fun loadData() {
        val mediaItem = MediaItem.Builder()
            .setUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
            .setMediaMetadata(
                Builder()
                    .setMediaType(MEDIA_TYPE_FOLDER_ALBUMS)
                    .setIsBrowsable(true)
                    .setArtworkUri(Uri.parse("https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"))
                    .setAlbumTitle("SoundHelix")
                    .setDisplayTitle("Song 1")
                    .build()
            ).build()

        mediaServiceHandler.addMediaItem(mediaItem)
    }

    fun formatDuration(duration: Long): String {
        val minutes: Long = TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS)
        val seconds: Long = (TimeUnit.SECONDS.convert(duration, TimeUnit.MILLISECONDS)
                - minutes * TimeUnit.SECONDS.convert(1, TimeUnit.MINUTES))
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun calculateProgressValues(currentProgress: Long) {
        progress = if (currentProgress > 0) (currentProgress.toFloat() / duration) else 0f
        progressString = formatDuration(currentProgress)
    }
}