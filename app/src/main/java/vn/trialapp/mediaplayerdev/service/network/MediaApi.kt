package vn.trialapp.mediaplayerdev.service.network

import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Query
import vn.trialapp.mediaplayerdev.models.response.SearchResponse
import vn.trialapp.mediaplayerdev.models.response.TestResponse

interface MediaApi {
    @GET("/v1/search?type=track&limit=1")
    suspend fun getSearchResponse(@Query("q") query: String): SearchResponse
}