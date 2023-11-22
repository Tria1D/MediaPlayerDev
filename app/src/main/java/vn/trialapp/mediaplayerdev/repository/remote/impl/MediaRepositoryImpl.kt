package vn.trialapp.mediaplayerdev.repository.remote.impl

import kotlinx.coroutines.CoroutineDispatcher
import vn.trialapp.mediaplayerdev.di.IoDispatcher
import vn.trialapp.mediaplayerdev.models.response.SearchResponse
import vn.trialapp.mediaplayerdev.repository.remote.MediaRepository
import vn.trialapp.mediaplayerdev.service.network.MediaApi
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import vn.trialapp.mediaplayerdev.utils.safeApiCall
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val retrofitInstance: MediaApi
): MediaRepository {

    private val TAG = "MediaRepositoryImpl"

    override suspend fun requestSearch(queryString: String): ResultStatus<SearchResponse> {
        val resultStatus = safeApiCall(dispatcher) {
            retrofitInstance.getSearchResponse(query = queryString)
        }
        return resultStatus
    }
}