package vn.trialapp.mediaplayerdev.usecases

import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.repository.remote.YoutubeRepository
import vn.trialapp.mediaplayerdev.utils.AppConstants.CommonConstants.MAX_RESULTS_FROM_YOUTUBE
import vn.trialapp.mediaplayerdev.utils.AppUtils
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class SearchYTUseCase @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
    private val dataStoreRepository: DataStoreRepository
)  {

    suspend operator fun invoke(queryString: String): Boolean {
        return when (val resultStatus = youtubeRepository.requestSearchYT(queryString)) {
            is ResultStatus.Error -> {
                LogUtil.d("Error: $resultStatus")
                false
            }
            is ResultStatus.Success -> {
                LogUtil.d("Success: $resultStatus")
                resultStatus.data?.items?.get(0)?.id?.let { dataStoreRepository.saveVideoId(it.videoId) }
                true
            }
        }
    }
}