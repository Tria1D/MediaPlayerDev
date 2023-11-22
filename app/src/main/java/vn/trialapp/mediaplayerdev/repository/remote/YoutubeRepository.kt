package vn.trialapp.mediaplayerdev.repository.remote

import vn.trialapp.mediaplayerdev.models.response.SearchResponse
import vn.trialapp.mediaplayerdev.models.response.SearchYTResponse
import vn.trialapp.mediaplayerdev.utils.ResultStatus

interface YoutubeRepository {
    suspend fun requestSearchYT(queryString: String): ResultStatus<SearchYTResponse>
}