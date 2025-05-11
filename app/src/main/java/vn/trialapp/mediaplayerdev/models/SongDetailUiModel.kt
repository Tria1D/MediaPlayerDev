package vn.trialapp.mediaplayerdev.models

import vn.trialapp.mediaplayerdev.features.songdetail.SongDetailUiEvent

data class SongDetailUiModel(
    val imageUrl: String = "",
    val songName: String = "",
    val songArtist: String = "",
    val isPlaying: Boolean,
    val durationString: String,
    val progress: Float,
    val progressString: String,
    val onUiEvent: (SongDetailUiEvent) -> Unit
)