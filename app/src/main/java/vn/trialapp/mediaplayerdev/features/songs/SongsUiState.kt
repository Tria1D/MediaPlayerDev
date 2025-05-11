package vn.trialapp.mediaplayerdev.features.songs

import vn.trialapp.mediaplayerdev.models.Song

sealed class SongsUiState {
    object Loading : SongsUiState()
    data class Success(val songs: List<Song>) : SongsUiState()
    data class Error(val message: String) : SongsUiState()
}