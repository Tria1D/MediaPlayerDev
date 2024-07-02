package vn.trialapp.mediaplayerdev.service.network

import retrofit2.http.GET
import retrofit2.http.Query
import vn.trialapp.mediaplayerdev.models.response.SearchResponse
import vn.trialapp.mediaplayerdev.utils.AppConstants.CommonConstants.MAX_RESULTS_FROM_YOUTUBE

interface MediaApi {
    @GET("/v1/search")
    suspend fun getSearchResponse(
        @Query("q") query: String,
        @Query("type") type: String ?= "track",
        @Query("limit") limit: Int ?= MAX_RESULTS_FROM_YOUTUBE
    ): SearchResponse
}