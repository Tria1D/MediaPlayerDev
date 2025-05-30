package vn.trialapp.mediaplayerdev.usecases

import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.repository.remote.MediaRepository
import vn.trialapp.mediaplayerdev.utils.AppConstants.CommonConstants.MAX_RESULTS_FROM_YOUTUBE
import vn.trialapp.mediaplayerdev.utils.AppConstants.CommonConstants.MIN_RESULTS_FROM_YOUTUBE
import vn.trialapp.mediaplayerdev.utils.AppUtils
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(queryString: String): Boolean {
        return when (val resultStatus = mediaRepository.requestSearch(queryString)) {
            is ResultStatus.Error -> {
                LogUtil.d("Error: $resultStatus")
                false
            }
            is ResultStatus.Success -> {
                LogUtil.d("Success: $resultStatus")
                resultStatus.data?.tracks?.items?.get(AppUtils().getRandomInt(MIN_RESULTS_FROM_YOUTUBE, MAX_RESULTS_FROM_YOUTUBE))?.let {
                    dataStoreRepository.saveArtistName(it.name)
                    dataStoreRepository.saveSongName(it.album.artists[0].name)
                    dataStoreRepository.saveImageUrl(it.album.images[0].url)
                }
                true
            }
        }
    }
}