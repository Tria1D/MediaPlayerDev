package vn.trialapp.mediaplayerdev.service.network

import retrofit2.http.GET
import retrofit2.http.Query
import vn.trialapp.mediaplayerdev.models.response.SearchYTResponse
import vn.trialapp.mediaplayerdev.utils.AppConstants.YoutubeKey.API_KEY

interface YoutubeApi {
    @GET("youtube/v3/search")
    suspend fun getSearchYTResponse(
        @Query("q") query: String,
        @Query("part") part: String ?= "snippet",
        @Query("maxResults") maxResults: Int ?= 1,
        @Query("type") type: String ?= "video",
        @Query("key") key: String ?= API_KEY,
    ): SearchYTResponse
}