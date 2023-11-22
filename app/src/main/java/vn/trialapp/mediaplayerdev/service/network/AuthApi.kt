package vn.trialapp.mediaplayerdev.service.network

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import vn.trialapp.mediaplayerdev.models.response.AuthResponse

interface AuthApi {

    @FormUrlEncoded
    @POST("/api/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): AuthResponse
}