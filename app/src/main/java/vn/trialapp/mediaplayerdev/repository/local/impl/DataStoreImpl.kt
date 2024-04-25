package vn.trialapp.mediaplayerdev.repository.local.impl

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
import vn.trialapp.mediaplayerdev.utils.LogUtil
import java.io.IOException
import javax.inject.Inject

class DataStoreImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreRepository {

    companion object {
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        val ARTIST_NAME = stringPreferencesKey("artist_name")
        val SONG_NAME = stringPreferencesKey("song_name")
        val VIDEO_ID = stringPreferencesKey("video_id")
        val AUDIO_URL = stringPreferencesKey("audio_url")
        val IMAGE_URL = stringPreferencesKey("image_url")
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
            LogUtil.d("getAuthToken: $authToken")
            authToken
        }
    }

    override suspend fun saveAuthToken(authToken: String) {
        LogUtil.d("saveAuthToken: $authToken")
        dataStore.edit {
            it[AUTH_TOKEN] = authToken
        }
    }

    override suspend fun fetchAuth(): String {
        LogUtil.d("fetchAuth: " + dataStore.data.first()[AUTH_TOKEN])
        return dataStore.data.first()[AUTH_TOKEN] ?: ""
    }

    override suspend fun saveArtistName(artistName: String) {
        LogUtil.d("saveArtistName: $artistName")
        dataStore.edit {
            it[ARTIST_NAME] = artistName
        }
    }

    override suspend fun saveSongName(songName: String) {
        LogUtil.d("saveSongName: $songName")
        dataStore.edit {
            it[SONG_NAME] = songName
        }
    }

    override suspend fun fetchArtistName(): String {
        LogUtil.d("fetchArtistName: " + dataStore.data.first()[ARTIST_NAME])
        return dataStore.data.first()[ARTIST_NAME] ?: ""
    }

    override suspend fun fetchSongName(): String {
        LogUtil.d("fetchSongName: " + dataStore.data.first()[SONG_NAME])
        return dataStore.data.first()[SONG_NAME] ?: ""
    }

    override suspend fun saveVideoId(videoId: String) {
        LogUtil.d("saveVideoId: $videoId")
        dataStore.edit {
            it[VIDEO_ID] = videoId
        }
    }

    override suspend fun fetchVideoId(): String {
        LogUtil.d("fetchVideoId: " + dataStore.data.first()[VIDEO_ID])
        return dataStore.data.first()[VIDEO_ID] ?: ""
    }

    override suspend fun saveAudioUrl(audioUrl: String) {
        LogUtil.d("saveAudioUrl: $audioUrl")
        dataStore.edit {
            it[AUDIO_URL] = audioUrl
        }
    }

    override suspend fun fetchAudioUrl(): String {
        LogUtil.d("fetchAudioUrl: " + dataStore.data.first()[AUDIO_URL])
        return dataStore.data.first()[AUDIO_URL] ?: ""
    }

    override suspend fun saveImageUrl(imageUrl: String) {
        LogUtil.d("saveImageUrl: $imageUrl")
        dataStore.edit {
            it[IMAGE_URL] = imageUrl
        }
    }

    override suspend fun fetchImageUrl(): String {
        LogUtil.d("fetchImageUrl: " + dataStore.data.first()[IMAGE_URL])
        return dataStore.data.first()[IMAGE_URL] ?: ""
    }
}