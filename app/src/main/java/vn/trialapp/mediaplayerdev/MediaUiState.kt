package vn.trialapp.mediaplayerdev

sealed class MediaUiState {
    object Initial : MediaUiState()
    object Ready : MediaUiState()
}
