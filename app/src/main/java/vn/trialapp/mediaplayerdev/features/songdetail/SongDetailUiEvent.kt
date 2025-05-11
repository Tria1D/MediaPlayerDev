package vn.trialapp.mediaplayerdev.features.songdetail

sealed class SongDetailUiEvent {
    object PlayPause : SongDetailUiEvent()
    object Backward : SongDetailUiEvent()
    object Forward : SongDetailUiEvent()
    data class UpdateProgress(val newProgress: Float) : SongDetailUiEvent()
}
