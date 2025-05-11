package vn.trialapp.mediaplayerdev.features.songdetail

import vn.trialapp.mediaplayerdev.models.Song

sealed class SongDetailUiState {
    object None: SongDetailUiState()
    object Initial : SongDetailUiState()
    data class Ready(val song: Song) : SongDetailUiState()
}
