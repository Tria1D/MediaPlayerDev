package vn.trialapp.mediaplayerdev.viewmodels

sealed class MediaUiState {
    object None: MediaUiState()
    object Initial : MediaUiState()
    object Ready : MediaUiState()
}
