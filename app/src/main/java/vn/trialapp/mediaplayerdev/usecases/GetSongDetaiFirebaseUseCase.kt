package vn.trialapp.mediaplayerdev.usecases

import vn.trialapp.mediaplayerdev.models.Song
import vn.trialapp.mediaplayerdev.models.remote.MusicDatabase
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class GetSongDetaiFirebaseUseCase @Inject constructor(
    private val musicDatabase: MusicDatabase
) {
    suspend operator fun invoke(index: Int): ResultStatus<Song> {
        val result = musicDatabase.getSong(index)

        return if (result.songUrl.isNotEmpty()) {
            LogUtil.d("GetSongDetaiFirebaseUseCase result: $result")
            ResultStatus.Success(result)
        } else {
            LogUtil.d("GetSongDetaiFirebaseUseCase is empty")
            ResultStatus.Error("No song found")
        }
    }
}