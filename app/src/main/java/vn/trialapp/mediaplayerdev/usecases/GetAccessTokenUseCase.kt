package vn.trialapp.mediaplayerdev.usecases

import android.util.Log
import vn.trialapp.mediaplayerdev.repository.local.DataStoreRepository
import vn.trialapp.mediaplayerdev.repository.remote.AuthRepository
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val dataStoreRepository: DataStoreRepository
) {
    private val TAG = "GetAccessTokenUseCase"

    suspend operator fun invoke(): Boolean {
        return when (val resultStatus = authRepository.requestGetAccessToken()) {
            is ResultStatus.Error -> {
                Log.d(TAG, "Error: $resultStatus")
                false
            }
            is ResultStatus.Success -> {
                Log.d(TAG, "Success: $resultStatus")
                resultStatus.data?.accessToken?.let {
                    dataStoreRepository.saveAuthToken(it)
                }
                true
            }
        }
    }
}