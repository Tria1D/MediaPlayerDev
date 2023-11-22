package vn.trialapp.mediaplayerdev.models.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @SerializedName("access_token")
    var accessToken: String? = null,

    @SerializedName("expires_in")
    var expiresIn: Number,

    @SerializedName("token_type")
    var tokenType: String? = null
)
