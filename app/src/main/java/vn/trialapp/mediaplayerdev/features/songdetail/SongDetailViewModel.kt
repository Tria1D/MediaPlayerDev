package vn.trialapp.mediaplayerdev.features.songdetail

import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import vn.trialapp.mediaplayerdev.models.Song
import vn.trialapp.mediaplayerdev.service.media.MediaServiceState
import vn.trialapp.mediaplayerdev.service.media.PlayerEvent
import vn.trialapp.mediaplayerdev.usecases.DownloadYTUseCase
import vn.trialapp.mediaplayerdev.usecases.GetAccessTokenUseCase
import vn.trialapp.mediaplayerdev.usecases.GetSongDetaiFirebaseUseCase
import vn.trialapp.mediaplayerdev.usecases.SearchUseCase
import vn.trialapp.mediaplayerdev.usecases.SearchYTUseCase
import vn.trialapp.mediaplayerdev.utils.AppConstants.SongDetailScreen.INITIAL_DURATION
import vn.trialapp.mediaplayerdev.utils.AppConstants.SongDetailScreen.INITIAL_IMAGE_URL
import vn.trialapp.mediaplayerdev.utils.AppConstants.SongDetailScreen.INITIAL_PROGRESS
import vn.trialapp.mediaplayerdev.utils.AppConstants.SongDetailScreen.INITIAL_PROGRESS_STRING
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.utils.ResultStatus

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class SongDetailViewModel @Inject constructor(
    private val mediaServiceHandler: MediaServiceHandler,
    savedStateHandle: SavedStateHandle,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val searchUseCase: SearchUseCase,
    private val searchYTUseCase: SearchYTUseCase,
    private val downloadYTUseCase: DownloadYTUseCase,
    private val getSongDetailFirebaseUseCase: GetSongDetaiFirebaseUseCase
): ViewModel() {

    var duration by savedStateHandle.saveable { mutableLongStateOf(INITIAL_DURATION) }
    var progress by savedStateHandle.saveable { mutableFloatStateOf(INITIAL_PROGRESS) }
    var progressString by savedStateHandle.saveable { mutableStateOf(INITIAL_PROGRESS_STRING) }
    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    var imageUrl by savedStateHandle.saveable { mutableStateOf(INITIAL_IMAGE_URL) }
    private var currentSong: Song? = null

    private val _uiState = MutableStateFlow<SongDetailUiState>(SongDetailUiState.None)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
//            requestGetAccessToken()
        }
    }

    private suspend fun onMediaServiceState() {
        mediaServiceHandler.mediaState.collect { mediaState ->
            when (mediaState) {
                is MediaServiceState.Buffering -> calculateProgressValues(mediaState.progress)
                is MediaServiceState.Initial -> _uiState.value = SongDetailUiState.Initial
                is MediaServiceState.Playing -> isPlaying = mediaState.isPlaying
                is MediaServiceState.Progress -> calculateProgressValues(mediaState.progress)
                is MediaServiceState.Ready -> {
                    duration = mediaState.duration
                    currentSong?.let { song ->
                        _uiState.value = SongDetailUiState.Ready(
                            song = Song(
                                title = song.title,
                                artist = song.artist,
                                imageUrl = song.imageUrl
                            )
                        )
                    }
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

    fun onUiEvent(uiEvent: SongDetailUiEvent) = viewModelScope.launch {
        when (uiEvent) {
            is SongDetailUiEvent.Backward -> mediaServiceHandler.onPlayerEvent(PlayerEvent.Backward)
            is SongDetailUiEvent.Forward -> mediaServiceHandler.onPlayerEvent(PlayerEvent.Forward)
            is SongDetailUiEvent.PlayPause -> mediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
            is SongDetailUiEvent.UpdateProgress -> {
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
            _uiState.value = SongDetailUiState.Initial
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
        val (audioUrlDownloaded, imageUrlDownloaded) = downloadYTUseCase()
        if (audioUrlDownloaded != null) {
            imageUrl = imageUrlDownloaded?:""
            loadData(audioUrlDownloaded)
            onMediaServiceState()
        } else {
            LogUtil.d("requestSearchYT else")
        }
        LogUtil.traceOut()
    }

    fun requestGetSongDetailFirebase(index: Int) {
        viewModelScope.launch {
            _uiState.value = SongDetailUiState.Initial
            when (val result = getSongDetailFirebaseUseCase(index)) {
                is ResultStatus.Success -> {
                    val song = result.data
                    if (song != null) {
                        currentSong = song
                        loadData(song.songUrl)
                        imageUrl = song.imageUrl
                        onMediaServiceState()
                    }
                }

                is ResultStatus.Error -> {
                    val errorMessage = result.message ?: "Unknown error"
                    LogUtil.d(errorMessage)
                }
            }
        }
    }
}