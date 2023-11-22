package vn.trialapp.mediaplayerdev.usecases

import android.util.Log
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.repository.remote.YoutubeRepository
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class SearchYTUseCase @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
    private val dataStoreRepository: DataStoreRepository
)  {
    private val TAG = "SearchYTUseCase"

    suspend operator fun invoke(queryString: String): Boolean {
        return when (val resultStatus = youtubeRepository.requestSearchYT(queryString)) {
            is ResultStatus.Error -> {
                Log.d(TAG, "Error: $resultStatus")
                false
            }
            is ResultStatus.Success -> {
                Log.d(TAG, "Success: $resultStatus")
                resultStatus.data?.items?.get(0)?.id?.let { dataStoreRepository.saveVideoId(it.videoId) }
                true
            }
        }
    }
}