package vn.trialapp.mediaplayerdev.usecases

import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.repository.remote.AuthRepository
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) {

    suspend operator fun invoke(): Boolean {
        return when (val resultStatus = authRepository.requestGetAccessToken()) {
            is ResultStatus.Error -> {
                LogUtil.d("Error: $resultStatus")
                false
            }
            is ResultStatus.Success -> {
                LogUtil.d("Success: $resultStatus")
                resultStatus.data?.accessToken?.let {
                    dataStoreRepository.saveAuthToken(it)
                }
                true
            }
        }
    }
}