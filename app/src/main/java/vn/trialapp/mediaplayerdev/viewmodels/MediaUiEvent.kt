package vn.trialapp.mediaplayerdev.viewmodels

sealed class MediaUiEvent {
    object PlayPause : MediaUiEvent()
    object Backward : MediaUiEvent()
    object Forward : MediaUiEvent()
    data class UpdateProgress(val newProgress: Float) : MediaUiEvent()
}
