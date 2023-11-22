package vn.trialapp.mediaplayerdev.repository.local.impl

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import java.io.IOException
import javax.inject.Inject

class DataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {

    private val TAG = "DataStoreImpl"

    companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val ARTIST_NAME = stringPreferencesKey("artist_name")
        val SONG_NAME = stringPreferencesKey("song_name")
        val VIDEO_ID = stringPreferencesKey("video_id")
        val AUDIO_URL = stringPreferencesKey("audio_url")
    }

    override fun getAuthToken(): Flow<String> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            val authToken = it[AUTH_TOKEN] ?: ""
            Log.d(TAG, "getAuthToken: $authToken")
            authToken
        }
    }

    override suspend fun saveAuthToken(authToken: String) {
        Log.d(TAG, "saveAuthToken: $authToken")
        dataStore.edit {
            it[AUTH_TOKEN] = authToken
        }
    }

    override suspend fun fetchAuth(): String {
        Log.d(TAG, "fetchAuth: " + dataStore.data.first()[AUTH_TOKEN])
        return dataStore.data.first()[AUTH_TOKEN] ?: ""
    }

    override suspend fun saveArtistName(artistName: String) {
        Log.d(TAG, "saveArtistName: $artistName")
        dataStore.edit {
            it[ARTIST_NAME] = artistName
        }
    }

    override suspend fun saveSongName(songName: String) {
        Log.d(TAG, "saveSongName: $songName")
        dataStore.edit {
            it[SONG_NAME] = songName
        }
    }

    override suspend fun fetchArtistName(): String {
        Log.d(TAG, "fetchArtistName: " + dataStore.data.first()[ARTIST_NAME])
        return dataStore.data.first()[ARTIST_NAME] ?: ""
    }

    override suspend fun fetchSongName(): String {
        Log.d(TAG, "fetchSongName: " + dataStore.data.first()[SONG_NAME])
        return dataStore.data.first()[SONG_NAME] ?: ""
    }

    override suspend fun saveVideoId(videoId: String) {
        Log.d(TAG, "saveVideoId: $videoId")
        dataStore.edit {
            it[VIDEO_ID] = videoId
        }
    }

    override suspend fun fetchVideoId(): String {
        Log.d(TAG, "fetchVideoId: " + dataStore.data.first()[VIDEO_ID])
        return dataStore.data.first()[VIDEO_ID] ?: ""
    }

    override suspend fun saveAudioUrl(audioUrl: String) {
        Log.d(TAG, "saveAudioUrl: $audioUrl")
        dataStore.edit {
            it[AUDIO_URL] = audioUrl
        }
    }

    override suspend fun fetchAudioUrl(): String {
        Log.d(TAG, "fetchAudioUrl: " + dataStore.data.first()[AUDIO_URL])
        return dataStore.data.first()[AUDIO_URL] ?: ""
    }
}