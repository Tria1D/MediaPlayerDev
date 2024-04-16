package vn.trialapp.mediaplayerdev.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import vn.trialapp.mediaplayerdev.service.media.MediaServiceHandler
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import vn.trialapp.mediaplayerdev.service.media.MediaServiceState
import vn.trialapp.mediaplayerdev.service.media.PlayerEvent
import vn.trialapp.mediaplayerdev.usecases.DownloadYTUseCase
import vn.trialapp.mediaplayerdev.usecases.GetAccessTokenUseCase
import vn.trialapp.mediaplayerdev.usecases.SearchUseCase
import vn.trialapp.mediaplayerdev.usecases.SearchYTUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import vn.trialapp.mediaplayerdev.utils.LogUtil

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaServiceHandler: MediaServiceHandler,
    savedStateHandle: SavedStateHandle,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val searchUseCase: SearchUseCase,
    private val searchYTUseCase: SearchYTUseCase,
    private val downloadYTUseCase: DownloadYTUseCase
): ViewModel() {

    var duration by savedStateHandle.saveable { mutableStateOf(0L) }
    var progress by savedStateHandle.saveable { mutableStateOf(0f) }
    var progressString by savedStateHandle.saveable { mutableStateOf("00:00") }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }

    private val _uiState = MutableStateFlow<MediaUiState>(MediaUiState.None)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            requestGetAccessToken()
        }
    }

    private suspend fun onMediaServiceState() {
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

    override fun onCleared() {
        LogUtil.traceIn()
        viewModelScope.launch {
            mediaServiceHandler.onPlayerEvent(PlayerEvent.Stop)
        }
        LogUtil.traceOut()
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

    private fun loadData(audioUrl: String) {
        LogUtil.traceIn()
        val mediaItem = MediaItem.Builder()
            .setUri(audioUrl)
            .setMediaMetadata(
                Builder()
                    .setMediaType(MEDIA_TYPE_FOLDER_ALBUMS)
                    .setIsBrowsable(true)
//                    .setArtworkUri(Uri.parse("https://i.pinimg.com/736x/4b/02/1f/4b021f002b90ab163ef41aaaaa17c7a4.jpg"))
//                    .setAlbumTitle("SoundHelix")
//                    .setDisplayTitle("Song 1")
                    .build()
            ).build()

        mediaServiceHandler.addMediaItem(mediaItem)
        LogUtil.traceOut()
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

    private suspend fun requestGetAccessToken() {
        LogUtil.traceIn()
        getAccessTokenUseCase()
        LogUtil.traceOut()
    }

    fun requestSearch(queryString: String) {
        LogUtil.traceIn()
        viewModelScope.launch {
            _uiState.value = MediaUiState.Initial
            val isSearchUseCaseSucceeded = searchUseCase(queryString)
            if (isSearchUseCaseSucceeded) {
                requestSearchYT(queryString)
            } else {
                LogUtil.d("requestSearch fail")
            }
        }
        LogUtil.traceOut()
    }

    private suspend fun requestSearchYT(queryString: String) {
        LogUtil.traceIn()
        val isSearchYTSucceeded = searchYTUseCase(queryString)
        if (isSearchYTSucceeded) {
            requestDownloadYTAudio()
        } else {
            LogUtil.d("requestSearchYT else")
        }
        LogUtil.traceOut()
    }

    private suspend fun requestDownloadYTAudio() {
        LogUtil.traceIn()
        val audioUrlDownloaded = downloadYTUseCase()
        if (audioUrlDownloaded != null) {
            loadData(audioUrlDownloaded)
            onMediaServiceState()
        } else {
            LogUtil.d("requestSearchYT else")
        }
        LogUtil.traceOut()
    }
}