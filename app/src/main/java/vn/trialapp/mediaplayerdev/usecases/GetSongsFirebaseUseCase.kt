package vn.trialapp.mediaplayerdev.usecases

import vn.trialapp.mediaplayerdev.models.Song
import vn.trialapp.mediaplayerdev.models.remote.MusicDatabase
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class GetSongsFirebaseUseCase @Inject constructor(
    private val musicDatabase: MusicDatabase
) {

    suspend operator fun invoke(): ResultStatus<List<Song>> {
        val result = musicDatabase.getAllSongs()

        return if (result.isNotEmpty()) {
            LogUtil.d("GetSongsFirebaseUseCase result: $result")
            ResultStatus.Success(result)
        } else {
            LogUtil.d("GetSongsFirebaseUseCase is empty")
            ResultStatus.Error("No songs found")
        }
    }
}