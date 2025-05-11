package vn.trialapp.mediaplayerdev.models.remote

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import vn.trialapp.mediaplayerdev.models.Song
import vn.trialapp.mediaplayerdev.utils.AppConstants.Firebase.SONG_COLLECTION

class MusicDatabase {

    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(SONG_COLLECTION)

    suspend fun getAllSongs(): List<Song> {
        return try {
            songCollection.get().await().toObjects(Song::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getSong(int: Int): Song {
        return try {
            val songList = songCollection.get().await().toObjects(Song::class.java)
            songList[int]
        } catch (e: Exception) {
            Song()
        }
    }
}