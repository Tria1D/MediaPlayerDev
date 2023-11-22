package vn.trialapp.mediaplayerdev.repository.remote.impl

import kotlinx.coroutines.CoroutineDispatcher
import vn.trialapp.mediaplayerdev.di.IoDispatcher
import vn.trialapp.mediaplayerdev.models.response.SearchYTResponse
import vn.trialapp.mediaplayerdev.repository.remote.YoutubeRepository
import vn.trialapp.mediaplayerdev.service.network.YoutubeApi
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import vn.trialapp.mediaplayerdev.utils.safeApiCall
import javax.inject.Inject

class YoutubeRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val retrofitInstance: YoutubeApi
): YoutubeRepository {

    private val TAG = "YoutubeRepositoryImpl"

    override suspend fun requestSearchYT(queryString: String): ResultStatus<SearchYTResponse> {
        val resultStatus = safeApiCall(dispatcher) {
            retrofitInstance.getSearchYTResponse(query = queryString)
        }
        return resultStatus
    }
}