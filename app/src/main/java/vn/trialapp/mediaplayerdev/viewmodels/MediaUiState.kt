package vn.trialapp.mediaplayerdev.viewmodels

sealed class MediaUiState {
    object Initial : MediaUiState()
    object Ready : MediaUiState()
}
