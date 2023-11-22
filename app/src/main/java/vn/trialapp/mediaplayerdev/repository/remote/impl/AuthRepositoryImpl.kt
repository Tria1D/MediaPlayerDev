package vn.trialapp.mediaplayerdev.repository.remote.impl

import kotlinx.coroutines.CoroutineDispatcher
import vn.trialapp.mediaplayerdev.di.IoDispatcher
import vn.trialapp.mediaplayerdev.models.response.AuthResponse
import vn.trialapp.mediaplayerdev.repository.remote.AuthRepository
import vn.trialapp.mediaplayerdev.service.network.AuthApi
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import vn.trialapp.mediaplayerdev.utils.safeApiCall
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val authRetrofitInstance: AuthApi
): AuthRepository {

    companion object {
        private const val CLIENT_ID = ""
        private const val CLIENT_SECRET = ""
    }

    override suspend fun requestGetAccessToken(): ResultStatus<AuthResponse> {
        val resultStatus = safeApiCall(dispatcher) {
            authRetrofitInstance.getAccessToken(
                clientId = CLIENT_ID,
                clientSecret = CLIENT_SECRET
            )
        }
        return resultStatus
    }
}