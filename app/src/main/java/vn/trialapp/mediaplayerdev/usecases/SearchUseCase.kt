package vn.trialapp.mediaplayerdev.usecases

import android.util.Log
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.repository.remote.MediaRepository
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    private val TAG = "SearchUseCase"

    suspend operator fun invoke(queryString: String): Boolean {
        return when (val resultStatus = mediaRepository.requestSearch(queryString)) {
            is ResultStatus.Error -> {
                Log.d(TAG, "Error: $resultStatus")
                false
            }
            is ResultStatus.Success -> {
                Log.d(TAG, "Success: $resultStatus")
                resultStatus.data?.tracks?.items?.get(0)?.let {
                    dataStoreRepository.saveArtistName(it.name)
                    dataStoreRepository.saveSongName(it.album.artists[0].name)
                }
                true
            }
        }
    }
}