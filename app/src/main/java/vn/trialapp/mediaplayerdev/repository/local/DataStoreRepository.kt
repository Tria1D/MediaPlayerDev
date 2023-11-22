package vn.trialapp.mediaplayerdev.repository.local

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getAuthToken(): Flow<String>
    suspend fun saveAuthToken(authToken: String)
    suspend fun fetchAuth(): String
    suspend fun saveArtistName(artistName: String)
    suspend fun fetchArtistName(): String
    suspend fun saveSongName(songName: String)
    suspend fun fetchSongName(): String
    suspend fun saveVideoId(videoId: String)
    suspend fun fetchVideoId(): String
    suspend fun saveAudioUrl(audioUrl: String)
    suspend fun fetchAudioUrl(): String
}