package vn.trialapp.mediaplayerdev.service.media

sealed class MediaServiceState {
    object Initial: MediaServiceState()
    data class Ready(val duration: Long): MediaServiceState()
    data class Progress(val progress: Long): MediaServiceState()
    data class Buffering(val progress: Long): MediaServiceState()
    data class Playing(val isPlaying: Boolean): MediaServiceState()
}
