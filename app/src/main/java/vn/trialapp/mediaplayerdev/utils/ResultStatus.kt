package vn.trialapp.mediaplayerdev.utils

sealed class ResultStatus<out ResponseType>(
    val data: ResponseType? = null,
    val message: String? = null
) {
    class Success<out ResponseType>(data: ResponseType): ResultStatus<ResponseType>(data)
    class Error<out ResponseType>(message: String, data: ResponseType? = null): ResultStatus<ResponseType>(data, message)
}