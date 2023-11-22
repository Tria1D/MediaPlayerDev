package vn.trialapp.mediaplayerdev.repository.remote

import vn.trialapp.mediaplayerdev.models.response.AuthResponse
import vn.trialapp.mediaplayerdev.utils.ResultStatus

interface AuthRepository {
    suspend fun requestGetAccessToken(): ResultStatus<AuthResponse>
}