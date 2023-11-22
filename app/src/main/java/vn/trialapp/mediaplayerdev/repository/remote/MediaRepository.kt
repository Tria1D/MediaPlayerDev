package vn.trialapp.mediaplayerdev.repository.remote

import vn.trialapp.mediaplayerdev.models.response.SearchResponse
import vn.trialapp.mediaplayerdev.utils.ResultStatus

interface MediaRepository {
    suspend fun requestSearch(queryString: String): ResultStatus<SearchResponse>
}